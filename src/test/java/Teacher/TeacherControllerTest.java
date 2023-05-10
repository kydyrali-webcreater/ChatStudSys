package Teacher;

import org.example.controller.TeacherController;
import org.example.model.Attendance;
import org.example.model.Dto.StudentAttendance;
import org.example.model.Dto.Student;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.example.service.TeacherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TeacherControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    @Test
    public void testSchedulerTeacher() {
        // Arrange
        String teacherId = "123";
        User teacher = new User();
        teacher.setId(teacherId);
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject());

    }

    @Test
    public void testSearchStudent() {
        // Arrange
        String teacherId = "123";
        String studentId = "456";
        User teacher = new User();
        teacher.setId(teacherId);
        List<Student> students = new ArrayList<>();

    }

    @Test
    public void testAllStudents() {
        // Arrange
        String teacherId = "123";
        User teacher = new User();
        teacher.setId(teacherId);
        List<String> studentIds = Arrays.asList("456", "789");
        List<Student> students = new ArrayList<>();

    }

    @Test
    public void testTakeAttendances() {
        // Arrange
        String teacherId = "123";
        User teacher = new User();
        teacher.setId(teacherId);
        Set<StudentAttendance> studentAttendances = new HashSet<>();
        studentAttendances.add(new StudentAttendance());
        List<Attendance> attendances = new ArrayList<>();
        attendances.add(new Attendance());

    }
}
