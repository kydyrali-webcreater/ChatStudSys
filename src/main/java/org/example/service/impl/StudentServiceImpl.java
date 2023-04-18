package org.example.service.impl;

import org.example.Exceptions.models.BasicException;
import org.example.model.Attendance;
import org.example.model.Dto.Student;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    @Transactional
    public Attendance takeAttendance(String studentId, Attendance attendance) {
        User student = userRepository.findByUserId(studentId)
                .orElseThrow(() -> new BasicException("STUDENT NOT FOUND"));

        Subject subject = subjectRepository.getByCourseCode(attendance.getCourseCode())
                .orElseThrow(() -> new BasicException("SUBJECT NOT FOUND"));

        if(!studentId.equals(attendance.getStudentId())){
            throw new BasicException("ATTENDANCE'S STUDENTID NOT EQUALS TO CURRENT STUDENT");
        }

        if(!Arrays.stream(subjectRepository.getListByCourseCode(attendance.getCourseCode()).split(",")).toList().contains(studentId)){
            throw new BasicException("STUNDENT DOESN'T HAVE THE COURSE");
        }

        Subject.WeekDay expectedDayOfWeek = subject.getWeekDay();
        DayOfWeek actualDayOfWeek = attendance.getTime().getDayOfWeek();
        if (checkWeekDay(expectedDayOfWeek , actualDayOfWeek)) {
            throw new BasicException("ATTENDANCE DAY OF WEEK NOT VALID");
        }

        if(!(subject.getTime().getHour()==attendance.getTime().getHour() &&
           subject.getTime().getMinute()<=attendance.getTime().getMinute() &&
                (subject.getTime().getMinute()+50)>=attendance.getTime().getMinute())){
            throw new BasicException("ATTENDANCE TIME NOT VALID");
        }

        return attendanceRepository.save(attendance);
    }

    private boolean checkWeekDay(Subject.WeekDay subjectDay , DayOfWeek attDay){
        int i = 0;
        switch (subjectDay){
            case MONDAY -> i=1;
            case TUESDAY -> i=2;
            case WEDNESDAY -> i=3;
            case THURSDAY -> i=4;
            case FRIDAY -> i=5;
            case SATURDAY -> i=6;
            case SUNDAY -> i=7;
        }
        if(attDay.getValue()!=i){
            return true;
        }
        return false;
    }
}
