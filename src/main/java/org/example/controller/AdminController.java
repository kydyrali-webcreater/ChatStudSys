package org.example.controller;

import org.example.model.Dto.Student;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {


    @Autowired
    private SubjectRepository subjectsRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/subject/create")
    public void create(@RequestBody Subject subject){
        subjectsRepository.save(subject);
    }

    @GetMapping("/{studentId}/subjects")
    public List<Subject> getListOfSubject(@PathVariable("studentId") String studentId){
        return subjectService.getList(studentId);
    }

    @GetMapping("/{subjectCode}/students")
    public List<Student> getListOfStudent(@PathVariable("subjectCode") String subjectId){
          return subjectService.getStudentList(subjectId);
    }

    @PutMapping("/{studentId}/attendances/{attendanceId}/change/{isEnable}")
    public void changeAttendance(@PathVariable("studentId") String studentId,
                                    @PathVariable("attendanceId") Long attendaceId,
                                    @PathVariable("isEnable") boolean isEnable){
        attendanceRepository.updateAttendanceStatus(attendaceId , isEnable);
    }

}
