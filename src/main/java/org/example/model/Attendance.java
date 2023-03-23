package org.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
@Getter @Setter
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time;

    private boolean isAttendance;

    private String studentId;

    private String courseCode;

    @Column(name = "puted_by_id")
    private String putedById;

    @Enumerated(EnumType.STRING)
    @Column(name = "puted_by_role")
    private PutedByRole putedByRole;

    @Enumerated(EnumType.STRING)
    private AttendanceType attendanceType;


    private enum PutedByRole{
        STUDENT,
        TEACHER,
        ADMIN;
    }

    public enum AttendanceType{
        CARD,
        PORTAL;
    }
}
