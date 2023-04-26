package org.example.service;

import org.example.model.Attendance;
import org.example.model.Dto.AttendanceListByCourseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    Attendance takeAttendance(String studentId , Attendance attendance);

    List<AttendanceListByCourseDto> getAllAttendance(String studentId);
}
