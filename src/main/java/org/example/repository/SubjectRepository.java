package org.example.repository;

import org.example.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s WHERE s.studentIds LIKE CONCAT('%', TRIM(:studentId), '%')")
    List<Subject> getListByStudentId(@Param("studentId") String studentId);

    @Query("SELECT s.studentIds FROM Subject s WHERE s.courseCode = :courseCode")
    String getListByCourseCode(@Param("courseCode") String studentId);

    @Query("SELECT s.studentIds FROM Subject s WHERE s.id = :id")
    String getListByCourseId(@Param("id") Long id);

    @Query("SELECT s FROM Subject s WHERE s.teacherId = :teacherId")
    List<Subject> getListByTeacherId(@Param("teacherId") String teacherId);

    @Query("SELECT s.studentIds FROM Subject s WHERE s.teacherId = :teacherId")
    String getListStudentsByTeacherId(@Param("teacherId") String teacherId);

    @Query("SELECT s.courseCode FROM Subject s WHERE s.teacherId= :teacherId")
    String getCourseCodeByTeacherId(@Param("teacherId") String teacherId);

    @Query("SELECT s FROM Subject  s WHERE s.courseCode = :courseCode")
    List<Subject> getByCourseCode(@Param("courseCode") String courseCode);
}