package org.example.service;

import org.example.model.Attendance;
import org.example.model.Dto.Student;
import org.example.model.Dto.StudentAttendance;
import org.example.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface TeacherService {
    List<Student> getListStudents(String studentId, String teacherId);

    @Transactional
    List<Attendance> setAttendance(User teacher, Set<StudentAttendance> studentAttendances, String StringtimeNow);
}
