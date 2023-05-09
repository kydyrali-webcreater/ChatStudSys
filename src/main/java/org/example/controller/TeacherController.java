package org.example.controller;

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
import org.example.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/teacher")
public class TeacherController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherService teacherService;



    @GetMapping("/{teacherId}/schedule")
    public List<Subject> schedulerTeacher(@PathVariable("teacherId") String id){
        User teacher = userRepository.findByUserId(id)
                .orElseThrow(() -> new BasicException("TEACHER NOT FOUND"));

        if(!teacher.getUserRole().name().equals("TEACHER")){
            throw new BasicException("USER ISN'T TEACHER");
        }

        return subjectRepository.getListByTeacherId(id);
    }

    @GetMapping("{teacherId}/students/search")
    public List<Student> searchStudent(
            @PathVariable("teacherId") String id,
            @Param(value = "studentId") String studentId){
        User teacher = userRepository.findByUserId(id)
                .orElseThrow(() -> new BasicException("TEACHER NOT FOUND"));

        if(!teacher.getUserRole().name().equals("TEACHER")){
            throw new BasicException("USER ISN'T TEACHER");
        }

        return teacherService.getListStudents(studentId , id);
    }

    @GetMapping("/{teacherId}/students")
    public List<Student> allStudents( @PathVariable("teacherId") String id){
        User teacher = userRepository.findByUserId(id)
                .orElseThrow(() -> new BasicException("TEACHER NOT FOUND"));

        if(!teacher.getUserRole().name().equals("TEACHER")){
            throw new BasicException("USER ISN'T TEACHER");
        }

        List<String> studentIds = Arrays.stream(subjectRepository.getListStudentsByTeacherId(id).split(",")).toList();
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


    @PutMapping("{teacherId}/students/attendance/take")
    public List<Attendance> takeAttendances(@PathVariable("teacherId") String id,
                                      @RequestBody Set<StudentAttendance> studentAttendances ,
                                       @RequestParam("time") String StringtimeNow){
        User teacher = userRepository.findByUserId(id)
                .orElseThrow(() -> new BasicException("USER NOT FOUND"));

        if(!teacher.getUserRole().name().equals("TEACHER")){
            throw new BasicException("USER ISN'T TEACHER");
        }

        return teacherService.setAttendance(teacher, studentAttendances , StringtimeNow);
    }
}
