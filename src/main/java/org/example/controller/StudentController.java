package org.example.controller;

import org.example.model.Attendance;
import org.example.model.Subject;
import org.example.repository.AttendanceRepository;
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



    @GetMapping("/{studentId}/subjects")
    public List<Subject> getListOfSubject(@PathVariable("studentId") String studentId){
        return subjectService.getList(studentId);
    }

    //TODO: сделать валидацию по времени , что бы мог ставить на урок который проходить сейчас и который принадлежить ему. поработать с ставляторами

    @PostMapping("/{studentId}/attendance/take")
    public Attendance takeAttendance(@Param("studentId") String studentId ,
                                     @RequestBody Attendance attendance){
        if(!studentId.equals(attendance.getStudentId())){

        }
        return attendanceRepository.save(attendance);
    }

    @GetMapping("/{studentId}/subjects/{courseCode}/attendance")
    public List<Attendance> getAttendanceByCourse(@PathVariable("studentId") String studentId ,
                                                  @PathVariable("courseCode") String courseCode){
        List<Attendance> attendanceList = attendanceRepository.getAttendanceByCourseCode(studentId , courseCode);
        return attendanceList;
    }

    @GetMapping("/{studentId}/subjects/attendance")
    public List<Attendance> getAllAttendance(@PathVariable("studentId") String studentId){
        return attendanceRepository.getAttendanceByStudentId(studentId);
    }
}
