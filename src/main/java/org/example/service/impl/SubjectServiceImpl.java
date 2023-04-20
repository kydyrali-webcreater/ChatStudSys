package org.example.service.impl;

import org.example.Exceptions.models.BasicException;
import org.example.model.Attendance;
import org.example.model.Dto.Student;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.example.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService{

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public List<Subject> getList(String studentId) {
        List<Subject> subjects = subjectRepository.getListByStudentId(studentId);

        for(Subject subject : subjects){
            User user = userRepository.findByUserId(studentId)
                    .orElseThrow(() -> new BasicException("NOT FOUND TEACHER BY ID:" + studentId));
            subject.setTeacherName(user.getLastname() + " " + user.getFirstname());
        }

        return subjects;

    }

    @Override
    public List<Student> getStudentList(String subjectCode) {
        String studentIds = subjectRepository.getListByCourseCode(subjectCode);
        String[] st = studentIds.split(",");
        List<Student> students = new ArrayList<>();
        for(String studentId : st){
            User user = userRepository.findByUserId(studentId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + studentId));
            Student student = new Student(user);
            student.setAttendanceList(attendanceRepository.getAttendanceByCourseCode(studentId , subjectCode));
            int numAttendance=0;
            int numAbsence=0;
            for(Attendance attendance : student.getAttendanceList()){
                if(attendance.isAttendance()){
                    numAttendance++;
                }
                else{
                    numAbsence++;
                }
            }
            student.setNumAbsence(numAbsence);
            student.setNumAttendance(numAttendance);
            students.add(student);
        }
        return students;
    }
}
