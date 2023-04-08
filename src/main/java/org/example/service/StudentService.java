package org.example.service;

import org.example.model.Attendance;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {

    public Attendance takeAttendance(String studentId , Attendance attendance);
}
