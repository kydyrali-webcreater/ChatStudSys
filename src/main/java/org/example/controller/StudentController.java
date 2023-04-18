package org.example.controller;

import org.example.Exceptions.models.BasicException;
import org.example.model.Attendance;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.UserRepository;
import org.example.service.StudentService;
import org.example.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentService studentService;



    @GetMapping("/{studentId}/subjects")
    public List<Subject> getListOfSubject(@PathVariable("studentId") String studentId){
        User student = userRepository.findByUserId(studentId)
                .orElseThrow(() -> new BasicException("STUDENT NOT FOUND"));
        return subjectService.getList(studentId);
    }


    @PostMapping("/{studentId}/attendance/take")
    public Attendance takeAttendance(@PathVariable("studentId") String studentId ,
                                     @RequestBody Attendance attendance){
        return studentService.takeAttendance(studentId , attendance);
    }

    @GetMapping("/{studentId}/subjects/{courseCode}/attendance")
    public List<Attendance> getAttendanceByCourse(@PathVariable("studentId") String studentId ,
                                                  @PathVariable("courseCode") String courseCode){
        User student = userRepository.findByUserId(studentId)
                .orElseThrow(() -> new BasicException("STUDENT NOT FOUND"));

        List<Attendance> attendanceList = attendanceRepository.getAttendanceByCourseCode(studentId , courseCode);

        for(Attendance attendance : attendanceList){
            User user = userRepository.findByUserId(studentId)
                    .orElseThrow(() -> new BasicException("NOT FOUND USER WHO TAKE ATTENDANCE"));
            attendance.setPutedByInfo(user.getLastname() + " " + user.getFirstname());
        }

        return attendanceList;
    }

    @GetMapping("/{studentId}/subjects/attendance")
    public List<Attendance> getAllAttendance(@PathVariable("studentId") String studentId){
        User student = userRepository.findByUserId(studentId)
                .orElseThrow(() -> new BasicException("STUDENT NOT FOUND"));

        List<Attendance> attendanceList = attendanceRepository.getAttendanceByStudentId(studentId);

        for(Attendance attendance : attendanceList){
            User user = userRepository.findByUserId(studentId)
                    .orElseThrow(() -> new BasicException("NOT FOUND USER WHO TAKE ATTENDANCE"));
            attendance.setPutedByInfo(user.getLastname() + " " + user.getFirstname());
        }

        return attendanceList;
    }
}
