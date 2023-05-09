package org.example.service.impl;

import org.example.Exceptions.models.BasicException;
import org.example.model.Attendance;
import org.example.model.Dto.Student;
import org.example.model.Dto.StudentAttendance;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.example.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        List<Subject> subjectList =  subjectRepository.getListByTeacherId(teacherId);
        Set<String> studentIds = new HashSet<>();
        for(Subject subject : subjectList) {
            studentIds.addAll(subject.getStudentIds().stream().filter(s -> s.startsWith(studentId)).collect(Collectors.toSet()));
        }

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
        LocalDateTime timeNow = LocalDateTime.now();
        List<Subject> subjectList = subjectRepository.getListByTeacherId(teacher.getId());

        Subject subjectNow=null;
        boolean IsNotFind = true;

        for(Subject subject : subjectList){
            Subject.WeekDay expectedDayOfWeek = subject.getWeekDay();
            DayOfWeek actualDayOfWeek = timeNow.getDayOfWeek();
            if(checkWeekDay(expectedDayOfWeek , actualDayOfWeek) &&
                    subject.getTime().getHour()==timeNow.getHour() &&
                    subject.getTime().getMinute()<=timeNow.getMinute() &&
                    (subject.getTime().getMinute()+50)>=timeNow.getMinute()){
                subjectNow=subject;
                IsNotFind=false;
                break;
            }
        }

        if(IsNotFind) {
            throw new BasicException("CAN'T FIND ANY SUBJECT IN THAT TIME OF PERIOD");
        }

        List<Attendance> attendanceList = new ArrayList<>();
        for(StudentAttendance studentAttendance: studentAttendances) {

            if (!subjectNow.getStudentIds().contains(studentAttendance.getStudentId())) {
                throw new BasicException("USER IS NOT TEACHER FOR THE STUDENT " + studentAttendance.getStudentId());
            }

            Attendance studentAtt = new Attendance();
            studentAtt.setAttendance(studentAttendance.isAttendance());
            studentAtt.setStudentId(studentAttendance.getStudentId());
            studentAtt.setAttendanceType(Attendance.AttendanceType.PORTAL);
            studentAtt.setTime(LocalDateTime.now());
            studentAtt.setCourseCode(subjectNow.getCourseCode());
            studentAtt.setPutedById(teacher.getId());
            studentAtt.setPutedByInfo(teacher.getLastname() + " " + teacher.getFirstname());
            studentAtt.setPutedByRole(Attendance.PutedByRole.TEACHER);
            attendanceRepository.save(studentAtt);
            attendanceList.add(studentAtt);
        }
        return attendanceList;
    }

    private boolean checkWeekDay(Subject.WeekDay subjectDay , DayOfWeek attDay){
        if(attDay.name().equals(subjectDay.name())){
            return true;
        }
        return false;
    }
}
