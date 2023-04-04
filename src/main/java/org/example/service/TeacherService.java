package org.example.service;

import org.example.model.Attendance;
import org.example.model.Dto.Student;

import java.util.List;

public interface TeacherService {
    List<Student> getListStudents(String studentId, String teacherId);

    Attendance setAttendance(String studentId, String teacherId, boolean attendance);
}
