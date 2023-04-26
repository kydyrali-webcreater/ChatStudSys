package org.example.service.impl;

import org.example.Exceptions.models.BasicException;
import org.example.model.Attendance;
import org.example.model.Dto.AttendanceListByCourseDto;
import org.example.model.Dto.Student;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        List<Subject> subjects = subjectRepository.getByCourseCode(attendance.getCourseCode());
        if(subjects.isEmpty()){
            throw new BasicException("NOT FOUND COURSE");
        }
        Subject subject = subjects.stream().filter(s -> s.getId() == attendance.getSubjectId()).collect(Collectors.toList()).get(0);
        if(!studentId.equals(attendance.getStudentId())){
            throw new BasicException("ATTENDANCE'S STUDENTID NOT EQUALS TO CURRENT STUDENT");
        }

        if(!Arrays.stream(subjectRepository.getListByCourseId(attendance.getSubjectId()).split(",")).toList().contains(studentId)){
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

        List<Attendance> lastAttendances = attendanceRepository.getLastAttendance(studentId, PageRequest.of(0, 10));
        Optional<Attendance> lastAttendance = lastAttendances.isEmpty() ? Optional.empty() : Optional.of(lastAttendances.get(0));
        if(lastAttendance.isPresent()) {
            if (checkDay(lastAttendance.get().getTime(), attendance.getTime())) {
                throw new BasicException("ATTENDANCE ALREADY PASSED");
            }
        }
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

    @Override
    public List<AttendanceListByCourseDto> getAllAttendance(String studentId) {
        List<Subject> subjects = subjectRepository.getListByStudentId(studentId);
        List<AttendanceListByCourseDto> list = new ArrayList<>();
        for(Subject subject : subjects){
            AttendanceListByCourseDto attendanceListByCourseDto = new AttendanceListByCourseDto();
            List<Attendance> attendanceList = attendanceRepository.getAttendanceByCourseCode(studentId , subject.getCourseCode());

            for(Attendance attendance : attendanceList){
                User user = userRepository.findByUserId(studentId)
                        .orElseThrow(() -> new BasicException("NOT FOUND USER WHO TAKE ATTENDANCE"));
                attendance.setPutedByInfo(user.getLastname() + " " + user.getFirstname());
            }
            attendanceListByCourseDto.setAttendanceList(attendanceList);
            attendanceListByCourseDto.setCourseCode(subject.getCourseCode());
            attendanceListByCourseDto.setCourseName(subject.getName());

            int numAttendance=0;
            int numAbsence=0;
            for(Attendance attendance : attendanceList){
                if(attendance.isAttendance()){
                    numAttendance++;
                }
                else{
                    numAbsence++;
                }
            }

            attendanceListByCourseDto.setAttendanceNum(numAttendance);
            attendanceListByCourseDto.setAbsenceNum(numAbsence);
            list.add(attendanceListByCourseDto);
        }
        return list;
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
