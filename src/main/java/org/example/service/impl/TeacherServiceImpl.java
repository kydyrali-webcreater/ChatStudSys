package org.example.service.impl;

import org.example.Exceptions.models.BasicException;
import org.example.model.Attendance;
import org.example.model.Dto.Student;
import org.example.model.Dto.StudentAttendance;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.example.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public List<Student> getListStudents(String studentId, String teacherId){
        List<String> studentIds = Arrays.stream(subjectRepository.getListStudentsByTeacherId(teacherId).split(","))
        .filter(s -> s.startsWith(studentId)).toList();
        if(studentIds.isEmpty()){
            throw new BasicException("TEACHER DOESN'T HAVE STUDENTS");
        }
        List<Student> studentList = new ArrayList<>();
        for(String ids: studentIds){
            User student = userRepository.findByUserId(ids)
                    .orElseThrow(() -> new BasicException("STUDENT NOT FOUND"));
            studentList.add(new Student(student));
        }

        return studentList;
    }

    @Override
    @Transactional
    public List<Attendance> setAttendance(User teacher, Set<StudentAttendance> studentAttendances){
        List<Attendance> attendanceList = new ArrayList<>();
        for(StudentAttendance studentAttendance: studentAttendances) {
            if (!Arrays.stream(subjectRepository.getListStudentsByTeacherId(teacher.getId()).split(",")).toList().contains(studentAttendance.getStudentId())) {
                throw new BasicException("USER IS NOT TEACHER FOR THE STUDENT " + studentAttendance.getStudentId());
            }

            Attendance studentAtt = new Attendance();
            studentAtt.setAttendance(studentAttendance.isAttendance());
            studentAtt.setStudentId(studentAttendance.getStudentId());
            studentAtt.setAttendanceType(Attendance.AttendanceType.PORTAL);
            studentAtt.setTime(LocalDateTime.now());
            studentAtt.setCourseCode(subjectRepository.getCourseCodeByTeacherId(teacher.getId()));
            studentAtt.setPutedById(teacher.getId());
            studentAtt.setPutedByInfo(teacher.getLastname() + " " + teacher.getFirstname());
            studentAtt.setPutedByRole(Attendance.PutedByRole.TEACHER);
            attendanceRepository.save(studentAtt);
            attendanceList.add(studentAtt);
        }
        return attendanceList;
    }
}
