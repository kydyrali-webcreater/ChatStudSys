package org.example.model.Dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Attendance;

import java.util.List;

@Getter
@Setter
public class AttendanceListByCourseDto {

    private String courseCode;

    private String courseName;

    private int attendanceNum;

    private int absenceNum;

    List<Attendance> attendanceList;
}
