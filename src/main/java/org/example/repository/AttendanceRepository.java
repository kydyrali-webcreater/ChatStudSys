package org.example.repository;

import org.example.model.Attendance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance , Long> {

    @Query("SELECT a from Attendance a WHERE a.studentId = :studentId and a.courseCode = :courseCode")
    List<Attendance> getAttendanceByCourseCode(@Param("studentId") String studentId,
                                               @Param("courseCode") String courseCode);

    @Query("SELECT a from Attendance a WHERE a.studentId = :studentId ")
    List<Attendance> getAttendanceByStudentId(@Param("studentId") String studentId);

//    @Modifying
//    @Transactional
//    @Query("UPDATE Attendance a SET a.isAttendance = :isAttendance , a.AttendanceType='PORTAL' WHERE a.id = :id")
//    int updateAttendanceStatus(@Param("id") Long id, @Param("isAttendance") boolean isAttendance);

    @Query("SELECT a from Attendance a WHERE a.studentId = :studentId ORDER BY a.id DESC ")
    List<Attendance> getLastAttendance(@Param("studentId") String studentId , Pageable pageable);
}
