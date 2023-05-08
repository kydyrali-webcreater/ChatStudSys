package org.example.controller;

import org.example.Exceptions.models.BasicException;
import org.example.model.Attendance;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.example.service.StudentService;
import org.hibernate.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class TakeAttendance {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;



    @PostMapping("/attendance/take")
    public Attendance takeAttendance(@RequestParam("studentId") String studentId,
                                        @RequestParam("time") String StringtimeNow){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime timeNow = LocalDateTime.parse(StringtimeNow, formatter);
        User student = userRepository.findByUserId(studentId)
                .orElseThrow(() -> new BasicException("STUDENT NOT FOUND"));

        List<Subject> subjectList = subjectRepository.getListByStudentId(studentId);
        if(subjectList.isEmpty()){
            throw new BasicException("NOT FOUND COURSE");
        }

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

        List<Attendance> lastAttendances = attendanceRepository.getLastAttendance(studentId, PageRequest.of(0, 10));
        Optional<Attendance> lastAttendance = lastAttendances.isEmpty() ? Optional.empty() : Optional.of(lastAttendances.get(0));
        if(lastAttendance.isPresent()) {
            if (checkDay(lastAttendance.get().getTime(), timeNow)) {
                throw new BasicException("ATTENDANCE ALREADY PASSED");
            }
        }

        Attendance attendance = new Attendance();
        attendance.setTime(timeNow);
        attendance.setAttendance(true);
        attendance.setStudentId(studentId);
        attendance.setSubjectId(subjectNow.getId());
        attendance.setCourseCode(subjectNow.getCourseCode());
        attendance.setPutedById(studentId);
        attendance.setPutedByRole(Attendance.PutedByRole.STUDENT);
        attendance.setPutedByInfo(student.getLastname() + " " + student.getFirstname());
        attendance.setAttendanceType(Attendance.AttendanceType.CARD);

        return attendanceRepository.save(attendance);
    }

    private boolean checkDay(LocalDateTime time , LocalDateTime pass){
        if(time.getDayOfYear()==pass.getDayOfYear()){
            if(time.getHour() == pass.getHour()){
                return true;
            }
        }
        return false;
    }

    private boolean checkWeekDay(Subject.WeekDay subjectDay , DayOfWeek attDay){
        if(attDay.name().equals(subjectDay.name())){
            return true;
        }
        return false;
    }
}
