//package Student;
//
//import org.example.Exceptions.models.BasicException;
//import org.example.model.Attendance;
//import org.example.model.Subject;
//import org.example.model.User;
//import org.example.repository.AttendanceRepository;
//import org.example.repository.SubjectRepository;
//import org.example.repository.UserRepository;
//import org.example.service.impl.StudentServiceImpl;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class StudentServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private AttendanceRepository attendanceRepository;
//
//    @Mock
//    private SubjectRepository subjectRepository;
//
//    @InjectMocks
//    private StudentServiceImpl studentService;
//
//    private User student;
//    private Subject subject;
//    private Attendance attendance;
//
//    @Before
//    public void setUp() {
//        student = new User();
//        student.setFirstname("John");
//        student.setLastname("Doe");
//
//        subject = new Subject();
//        subject.setCourseCode("MATH101");
//        subject.setTime(LocalTime.from(LocalDateTime.now()));
//
//        attendance = new Attendance();
//        attendance.setStudentId("123");
//        attendance.setCourseCode("MATH101");
//        attendance.setTime(LocalDateTime.now());
//    }
//
//    @Test
//    public void testTakeAttendance_validAttendance() {
//        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(Optional.of(student));
//        Mockito.when(subjectRepository.getByCourseCode(Mockito.anyString())).thenReturn(Optional.of(subject));
//        Mockito.when(subjectRepository.getListByCourseCode(Mockito.anyString())).thenReturn("123");
//        Mockito.when(attendanceRepository.save(Mockito.any(Attendance.class))).thenReturn(attendance);
//
//        Attendance result = studentService.takeAttendance("123", attendance);
//
//        Assert.assertEquals(attendance, result);
//    }
//
//    @Test(expected = BasicException.class)
//    public void testTakeAttendance_studentNotFound() {
//        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(Optional.empty());
//
//        studentService.takeAttendance("123", attendance);
//    }
//
//    @Test(expected = BasicException.class)
//    public void testTakeAttendance_subjectNotFound() {
//        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(Optional.of(student));
//        Mockito.when(subjectRepository.getByCourseCode(Mockito.anyString())).thenReturn(Optional.empty());
//
//        studentService.takeAttendance("123", attendance);
//    }
//
//    @Test(expected = BasicException.class)
//    public void testTakeAttendance_attendanceStudentIdMismatch() {
//        attendance.setStudentId("456");
//
//        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(Optional.of(student));
//        Mockito.when(subjectRepository.getByCourseCode(Mockito.anyString())).thenReturn(Optional.of(subject));
//
//        studentService.takeAttendance("123", attendance);
//    }
//
//    @Test(expected = BasicException.class)
//    public void testTakeAttendance_studentNotEnrolledInCourse() {
//        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(Optional.of(student));
//        Mockito.when(subjectRepository.getByCourseCode(Mockito.anyString())).thenReturn(Optional.of(subject));
//        Mockito.when(subjectRepository.getListByCourseCode(Mockito.anyString())).thenReturn("789");
//
//        studentService.takeAttendance("123", attendance);
//    }
//
//    @Test(expected = BasicException.class)
//    public void testTakeAttendance_attendanceTimeInvalid() {
//        subject.setTime(LocalTime.from(LocalDateTime.now().plusMinutes(70)));
//        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(Optional.of(student));
//        Mockito.when(subjectRepository.getByCourseCode(Mockito.anyString())).thenReturn(Optional.of(subject));
//        Mockito.when(subjectRepository.getListByCourseCode(Mockito.anyString())).thenReturn("123");
//
//        studentService.takeAttendance("123", attendance);
//    }
//}