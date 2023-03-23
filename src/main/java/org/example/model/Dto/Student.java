package org.example.model.Dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Attendance;
import org.example.model.User;

import java.util.List;

@Getter @Setter
public class Student {

    private String id;

    private String firstname;

    private String lastname;

    private List<Attendance> attendanceList;

    public Student(User user){
        id= user.getId();
        firstname= user.getFirstname();
        lastname= user.getLastname();
    }

    private int numAttendance;

    private int numAbsence;



}
