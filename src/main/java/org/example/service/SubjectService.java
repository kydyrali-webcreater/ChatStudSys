package org.example.service;

import org.example.model.Dto.Student;
import org.example.model.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubjectService {

    List<Subject> getList(String studentId);

    List<Student> getStudentList(String subjectId);
}
