package org.example.service.impl;

import org.example.model.Attendance;
import org.example.model.Dto.Student;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.example.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

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
            throw new InputMismatchException("TEACHER DOESN'T HAVE STUDENTS");
        }
        List<Student> studentList = new ArrayList<>();
        for(String ids: studentIds){
            User student = userRepository.findByUserId(ids)
                    .orElseThrow(() -> new UsernameNotFoundException("STUDENT NOT FOUND"));
            studentList.add(new Student(student));
        }

        return studentList;
    }

    @Override
    public Attendance setAttendance(String studentId, String teacherId, boolean attendance){
        User student = userRepository.findByUserId(studentId)
                .orElseThrow(() -> new UsernameNotFoundException("STUDENT NOT FOUND"));

        if(!Arrays.stream(subjectRepository.getListStudentsByTeacherId(teacherId).split(",")).toList().contains(studentId)){
            throw new InputMismatchException("USER IS NOT TEACHER FOR THE STUDENT");
        }

        return null;
    }
}
