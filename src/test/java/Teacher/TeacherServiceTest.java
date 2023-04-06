package Teacher;

import org.example.Exceptions.models.BasicException;
import org.example.controller.TeacherController;
import org.example.model.Attendance;
import org.example.model.Dto.StudentAttendance;
import org.example.model.Dto.Student;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.example.service.TeacherService;
import org.example.service.impl.TeacherServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

//TODO: Даработать
@RunWith(MockitoJUnitRunner.class)
public class TeacherServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    public void testGetListStudents() {
        String studentId = "123";
        String teacherId = "456";

        String studentIds = "123,234,345";
        Mockito.when(subjectRepository.getListStudentsByTeacherId(teacherId)).thenReturn(studentIds);

        User student1 = new User();
        student1.setId("123");
        User student2 = new User();
        student2.setId("234");

        Mockito.when(userRepository.findByUserId("123")).thenReturn(Optional.of(student1));
        Mockito.when(userRepository.findByUserId("234")).thenReturn(Optional.of(student2));

        List<Student> result = teacherService.getListStudents(studentId, teacherId);


        Mockito.verify(subjectRepository).getListStudentsByTeacherId(teacherId);
        Mockito.verify(userRepository).findByUserId("123");
        Mockito.verify(userRepository).findByUserId("234");
    }

    @Test
    public void testGetListStudents_noMatchingStudents() {
        String studentId = "123";
        String teacherId = "456";

        Mockito.when(subjectRepository.getListStudentsByTeacherId(teacherId)).thenReturn("");

        try {
            teacherService.getListStudents(studentId, teacherId);
            Assert.fail("Expected BasicException was not thrown");
        } catch (BasicException ex) {
            Assert.assertEquals("TEACHER DOESN'T HAVE STUDENTS", ex.getMessage());
        }

        Mockito.verify(subjectRepository).getListStudentsByTeacherId(teacherId);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetListStudents_studentNotFound() {
        String studentId = "123";
        String teacherId = "456";

        String studentIds = "123,234,345";
        Mockito.when(subjectRepository.getListStudentsByTeacherId(teacherId)).thenReturn(studentIds);

        User student1 = new User();
        student1.setId("123");
        User student2 = new User();
        student2.setId("234");

        Mockito.when(userRepository.findByUserId("123")).thenReturn(Optional.of(student1));
        Mockito.when(userRepository.findByUserId("234")).thenReturn(Optional.empty());

        try {
            teacherService.getListStudents(studentId, teacherId);
        } catch (BasicException ex) {
            Assert.assertEquals("STUDENT NOT FOUND", ex.getMessage());
        }

        Mockito.verify(subjectRepository).getListStudentsByTeacherId(teacherId);
        Mockito.verify(userRepository).findByUserId("123");
    }

    @Test
    public void testSetAttendance() {
        String teacherId = "123";
        User teacher = new User();
        teacher.setId(teacherId);

        Set<StudentAttendance> studentAttendances = new HashSet<>();
        StudentAttendance studentAttendance1 = new StudentAttendance();
        studentAttendance1.setStudentId("234");
        studentAttendance1.setAttendance(true);
        studentAttendances.add(studentAttendance1);
        StudentAttendance studentAttendance2 = new StudentAttendance();
        studentAttendance2.setStudentId("345");
        studentAttendance2.setAttendance(false);
        studentAttendances.add(studentAttendance2);

        String studentIds = "123,234,345";
        Mockito.when(subjectRepository.getListStudentsByTeacherId(teacherId)).thenReturn(studentIds);
        Mockito.when(subjectRepository.getCourseCodeByTeacherId(teacherId)).thenReturn("CS101");

        Attendance attendance1 = new Attendance();
        attendance1.setStudentId("234");
        attendance1.setAttendance(true);
        Attendance attendance2 = new Attendance();
        attendance2.setStudentId("345");
        attendance2.setAttendance(false);
        List<Attendance> expectedAttendances = Arrays.asList(attendance1, attendance2);

        Mockito.when(attendanceRepository.save(Mockito.any(Attendance.class))).thenReturn(attendance1, attendance2);

        List<Attendance> result = teacherService.setAttendance(teacher, studentAttendances);

        Mockito.verify(subjectRepository).getListStudentsByTeacherId(studentIds);
        Mockito.verify(subjectRepository).getCourseCodeByTeacherId(teacherId);
    }

    @Test
    public void testSetAttendance_studentNotAssignedToTeacher() {
        String teacherId = "123";
        User teacher = new User();
        teacher.setId(teacherId);

        Set<StudentAttendance> studentAttendances = new HashSet<>();
        StudentAttendance studentAttendance1 = new StudentAttendance();
        studentAttendance1.setStudentId("234");
        studentAttendance1.setAttendance(true);
        studentAttendances.add(studentAttendance1);
        StudentAttendance studentAttendance2 = new StudentAttendance();
        studentAttendance2.setStudentId("345");
        studentAttendance2.setAttendance(false);
        studentAttendances.add(studentAttendance2);

        String studentIds = "123,456,789";
        Mockito.when(subjectRepository.getListStudentsByTeacherId(teacherId)).thenReturn(studentIds);

        try {
            teacherService.setAttendance(teacher, studentAttendances);
            Assert.fail("Expected BasicException was not thrown");
        } catch (BasicException ex) {
            Assert.assertEquals("USER IS NOT TEACHER FOR THE STUDENT 345", ex.getMessage());
        }

        Mockito.verify(subjectRepository).getListStudentsByTeacherId(teacherId);
        Mockito.verifyNoMoreInteractions(subjectRepository);
        Mockito.verifyNoMoreInteractions(attendanceRepository);
    }
}
