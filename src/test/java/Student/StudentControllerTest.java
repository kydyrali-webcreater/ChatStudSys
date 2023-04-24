package Student;

import org.example.Exceptions.models.BasicException;
import org.example.model.Attendance;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.UserRepository;
import org.example.service.StudentService;
import org.example.service.SubjectService;
import org.example.controller.StudentController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private SubjectService subjectService;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudentService studentService;

    @Test
    public void testGetListOfSubjects() {
        // Mocking dependencies
        User student = new User();
        when(userRepository.findByUserId(eq("student1"))).thenReturn(Optional.of(student));
        when(subjectService.getList(eq("student1"))).thenReturn(Collections.emptyList());

        // Perform test
        List<Subject> subjects = studentController.getListOfSubject("student1");

        // Assert results
        assertNotNull(subjects);
        assertEquals(0, subjects.size());
    }

    @Test
    public void testTakeAttendance() {
        // Mocking dependencies
        Attendance attendance = new Attendance();
        attendance.setStudentId("student1");
        when(studentService.takeAttendance(eq("student1"), any(Attendance.class))).thenReturn(attendance);

        // Perform test
        Attendance result = studentController.takeAttendance("student1", attendance);

        // Assert results
        assertNotNull(result);
        assertEquals("student1", result.getStudentId());
    }

    @Test
    public void testGetAttendanceByCourse() {
        // Mocking dependencies
        User student = new User();
        when(userRepository.findByUserId(eq("student1"))).thenReturn(Optional.of(student));
        when(attendanceRepository.getAttendanceByCourseCode(eq("student1"), eq("course1"))).thenReturn(Collections.emptyList());

        // Perform test
        List<Attendance> attendanceList = studentController.getAttendanceByCourse("student1", "course1");

        // Assert results
        assertNotNull(attendanceList);
        assertEquals(0, attendanceList.size());
    }

//    @Test
//    public void testGetAllAttendance() {
//        // Mocking dependencies
//        User student = new User();
//        when(userRepository.findByUserId(eq("student1"))).thenReturn(Optional.of(student));
//        when(attendanceRepository.getAttendanceByStudentId(eq("student1"))).thenReturn(Collections.emptyList());
//
//        // Perform test
//        List<Attendance> attendanceList = studentController.getAllAttendance("student1");
//
//        // Assert results
//        assertNotNull(attendanceList);
//        assertEquals(0, attendanceList.size());
//    }
}
