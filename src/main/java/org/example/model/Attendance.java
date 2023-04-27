package org.example.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

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

    private Long subjectId;

    private String courseCode;

    @Column(name = "puted_by_id")
    private String putedById;

    @Enumerated(EnumType.STRING)
    @Column(name = "puted_by_role")
    private PutedByRole putedByRole;

    @Transient
    private String putedByInfo;
    @Transient
    private String subjectHour;

    @Enumerated(EnumType.STRING)
    private AttendanceType attendanceType;


    public enum PutedByRole{
        STUDENT,
        TEACHER,
        ADMIN;
    }

    public enum AttendanceType{
        CARD,
        PORTAL;
    }
}
