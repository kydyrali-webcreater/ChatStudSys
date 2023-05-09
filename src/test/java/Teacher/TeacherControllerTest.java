//package Teacher;
//
//import org.example.Exceptions.models.BasicException;
//import org.example.controller.TeacherController;
//import org.example.model.Attendance;
//import org.example.model.Dto.StudentAttendance;
//import org.example.model.Dto.Student;
//import org.example.model.Subject;
//import org.example.model.User;
//import org.example.repository.SubjectRepository;
//import org.example.repository.UserRepository;
//import org.example.service.TeacherService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TeacherControllerTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private SubjectRepository subjectRepository;
//
//    @Mock
//    private TeacherService teacherService;
//
//    @InjectMocks
//    private TeacherController teacherController;
//
//    @Test
//    public void testSchedulerTeacher() {
//        String teacherId = "123";
//        User teacher = new User();
//        teacher.setId(teacherId);
//        teacher.setUserRole(User.UserRole.TEACHER);
//
//        List<Subject> subjects = new ArrayList<>();
//        subjects.add(new Subject());
//        subjects.add(new Subject());
//
//        Mockito.when(userRepository.findByUserId(teacherId)).thenReturn(Optional.of(teacher));
//        Mockito.when(subjectRepository.getListByTeacherId(teacherId)).thenReturn(subjects);
//
//        List<Subject> result = teacherController.schedulerTeacher(teacherId);
//
//        Assert.assertEquals(subjects, result);
//        Mockito.verify(userRepository).findByUserId(teacherId);
//        Mockito.verify(subjectRepository).getListByTeacherId(teacherId);
//    }
//
//    @Test
//    public void testSchedulerTeacher_teacherNotFound() {
//        String teacherId = "123";
//
//        Mockito.when(userRepository.findByUserId(teacherId)).thenReturn(Optional.empty());
//
//        try {
//            teacherController.schedulerTeacher(teacherId);
//            Assert.fail("Expected BasicException was not thrown");
//        } catch (BasicException ex) {
//            Assert.assertEquals("TEACHER NOT FOUND", ex.getMessage());
//        }
//
//        Mockito.verify(userRepository).findByUserId(teacherId);
//        Mockito.verifyNoMoreInteractions(subjectRepository);
//    }
//
//    @Test
//    public void testSchedulerTeacher_userIsNotTeacher() {
//        String teacherId = "123";
//        User user = new User();
//        user.setUserRole(User.UserRole.STUDENT);
//
//        Mockito.when(userRepository.findByUserId(teacherId)).thenReturn(Optional.of(user));
//
//        try {
//            teacherController.schedulerTeacher(teacherId);
//            Assert.fail("Expected BasicException was not thrown");
//        } catch (BasicException ex) {
//            Assert.assertEquals("USER ISN'T TEACHER", ex.getMessage());
//        }
//
//        Mockito.verify(userRepository).findByUserId(teacherId);
//        Mockito.verifyNoMoreInteractions(subjectRepository);
//    }
//
//    @Test
//    public void testSearchStudent_teacherNotFound() {
//        String teacherId = "123";
//        String studentId = "456";
//
//        Mockito.when(userRepository.findByUserId(teacherId)).thenReturn(Optional.empty());
//
//        try {
//            teacherController.searchStudent(teacherId, studentId);
//            Assert.fail("Expected BasicException was not thrown");
//        } catch (BasicException ex) {
//            Assert.assertEquals("TEACHER NOT FOUND", ex.getMessage());
//        }
//
//        Mockito.verify(userRepository).findByUserId(teacherId);
//        Mockito.verifyNoMoreInteractions(teacherService);
//    }
//
//    @Test
//    public void testSearchStudent_userIsNotTeacher() {
//        String teacherId = "123";
//        String studentId = "456";
//        User user = new User();
//        user.setUserRole(User.UserRole.STUDENT);
//
//        Mockito.when(userRepository.findByUserId(teacherId)).thenReturn(Optional.of(user));
//
//        try {
//            teacherController.searchStudent(teacherId, studentId);
//            Assert.fail("Expected BasicException was not thrown");
//        } catch (BasicException ex) {
//            Assert.assertEquals("USER ISN'T TEACHER", ex.getMessage());
//        }
//
//        Mockito.verify(userRepository).findByUserId(teacherId);
//        Mockito.verifyNoMoreInteractions(teacherService);
//    }
//
//    @Test
//    public void testTakeAttendances() {
//        String teacherId = "123";
//        Set<StudentAttendance> studentAttendances = new HashSet<>();
//        studentAttendances.add(new StudentAttendance());
//
//        User teacher = new User();
//        teacher.setId(teacherId);
//        teacher.setUserRole(User.UserRole.TEACHER);
//
//        List<Attendance> attendances = new ArrayList<>();
//        attendances.add(new Attendance());
//        attendances.add(new Attendance());
//
//        Mockito.when(userRepository.findByUserId(teacherId)).thenReturn(Optional.of(teacher));
//        Mockito.when(teacherService.setAttendance(teacher, studentAttendances)).thenReturn(attendances);
//
//        List<Attendance> result = teacherController.takeAttendances(teacherId, studentAttendances);
//
//        Assert.assertEquals(attendances, result);
//        Mockito.verify(userRepository).findByUserId(teacherId);
//        Mockito.verify(teacherService).setAttendance(teacher, studentAttendances);
//    }
//
//    @Test
//    public void testTakeAttendances_userNotFound() {
//        String teacherId = "123";
//        Set<StudentAttendance> studentAttendances = new HashSet<>();
//
//        Mockito.when(userRepository.findByUserId(teacherId)).thenReturn(Optional.empty());
//
//        try {
//            teacherController.takeAttendances(teacherId, studentAttendances);
//            Assert.fail("Expected BasicException was not thrown");
//        } catch (BasicException ex) {
//            Assert.assertEquals("USER NOT FOUND", ex.getMessage());
//        }
//
//        Mockito.verify(userRepository).findByUserId(teacherId);
//        Mockito.verifyNoMoreInteractions(teacherService);
//    }
//
//    @Test
//    public void testTakeAttendances_userIsNotTeacher() {
//        String teacherId = "123";
//        Set<StudentAttendance> studentAttendances = new HashSet<>();
//        User user = new User();
//        user.setUserRole(User.UserRole.STUDENT);
//
//        Mockito.when(userRepository.findByUserId(teacherId)).thenReturn(Optional.of(user));
//
//        try {
//            teacherController.takeAttendances(teacherId, studentAttendances);
//            Assert.fail("Expected BasicException was not thrown");
//        } catch (BasicException ex) {
//            Assert.assertEquals("USER ISN'T TEACHER", ex.getMessage());
//        }
//
//        Mockito.verify(userRepository).findByUserId(teacherId);
//        Mockito.verifyNoMoreInteractions(teacherService);
//    }
//}
//
//
