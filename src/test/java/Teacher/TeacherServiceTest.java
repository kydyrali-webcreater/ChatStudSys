package Teacher;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.example.Exceptions.models.BasicException;
import org.example.model.Attendance;
import org.example.model.Dto.Student;
import org.example.model.Dto.StudentAttendance;
import org.example.model.User;
import org.example.service.impl.TeacherServiceImpl;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TeacherServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetListStudents() {
        String studentId = "123";
        String teacherId = "456";
        String studentIds = studentId + ",789";
        List<String> studentIdsList = Arrays.asList(studentIds.split(","));

        // Mocking the subjectRepository
        when(subjectRepository.getListStudentsByTeacherId(teacherId)).thenReturn(studentIds);

        // Mocking the userRepository for the first student
        User user1 = new User();
        user1.setId(studentId);
        when(userRepository.findByUserId(studentId)).thenReturn(Optional.of(user1));

        // Mocking the userRepository for the second student
        User user2 = new User();
        user2.setId("789");
        when(userRepository.findByUserId("789")).thenReturn(Optional.of(user2));

    }

    @Test
    public void testSetAttendance() {
        User teacher = new User();
        teacher.setId("123");

        Set<StudentAttendance> studentAttendances = new HashSet<>();

        StudentAttendance studentAttendance1 = new StudentAttendance();
        studentAttendance1.setStudentId("456");
        studentAttendance1.setAttendance(true);
        studentAttendances.add(studentAttendance1);

        // Mocking the subjectRepository
        when(subjectRepository.getListStudentsByTeacherId(teacher.getId())).thenReturn("456,789");

        Attendance attendance = new Attendance();
        attendance.setAttendance(true);
        attendance.setStudentId(studentAttendance1.getStudentId());
        attendance.setAttendanceType(Attendance.AttendanceType.PORTAL);
        attendance.setTime(LocalDateTime.now());
        attendance.setCourseCode(subjectRepository.getCourseCodeByTeacherId(teacher.getId()));
        attendance.setPutedById(teacher.getId());
        attendance.setPutedByInfo(teacher.getLastname() + " " + teacher.getFirstname());
        attendance.setPutedByRole(Attendance.PutedByRole.TEACHER);

        // Mocking the attendanceRepository
// Mocking the userRepository for the student
        User user = new User();
        user.setId(studentAttendance1.getStudentId());
        when(userRepository.findByUserId(studentAttendance1.getStudentId())).thenReturn(Optional.of(user));

    }
    @Test
    public void testGetAttendanceByStudentIdAndDate() {
        String studentId = "123";
        LocalDateTime date = LocalDateTime.of(2023, 5, 10, 10, 0);

        // Mocking the attendanceRepository
        Attendance attendance = new Attendance();
        attendance.setAttendance(true);
        attendance.setStudentId(studentId);
        attendance.setAttendanceType(Attendance.AttendanceType.PORTAL);
        attendance.setTime(date);
        attendance.setCourseCode("MATH101");
        attendance.setPutedById("456");
        attendance.setPutedByInfo("John Doe");
        attendance.setPutedByRole(Attendance.PutedByRole.TEACHER);

    }
    @Test
    public void testGetAttendanceByTeacherIdAndDate() {
        String teacherId = "456";
        LocalDateTime date = LocalDateTime.of(2023, 5, 10, 10, 0);

        // Mocking the attendanceRepository
        List<Attendance> attendances = new ArrayList<>();
        Attendance attendance1 = new Attendance();
        attendance1.setAttendance(true);
        attendance1.setStudentId("123");
        attendance1.setAttendanceType(Attendance.AttendanceType.PORTAL);
        attendance1.setTime(date);
        attendance1.setCourseCode("MATH101");
        attendance1.setPutedById(teacherId);
        attendance1.setPutedByInfo("John Doe");
        attendance1.setPutedByRole(Attendance.PutedByRole.TEACHER);
        attendances.add(attendance1);
        Attendance attendance2 = new Attendance();
        attendance2.setAttendance(false);
        attendance2.setStudentId("789");
        attendance2.setAttendanceType(Attendance.AttendanceType.PORTAL);
        attendance2.setTime(date);
        attendance2.setCourseCode("MATH101");
        attendance2.setPutedById(teacherId);
        attendance2.setPutedByInfo("John Doe");
        attendance2.setPutedByRole(Attendance.PutedByRole.TEACHER);
        attendances.add(attendance2);

    }
    @Test
    public void testGetStudentsWithMissedAttendance() {
        String teacherId = "456";
        LocalDateTime date = LocalDateTime.of(2023, 5, 10, 10, 0);

        // Mocking the subjectRepository
        when(subjectRepository.getListStudentsByTeacherId(teacherId)).thenReturn("123,456,789");

        // Mocking the attendanceRepository
        Attendance attendance1 = new Attendance();
        attendance1.setAttendance(true);
        attendance1.setStudentId("123");
        attendance1.setAttendanceType(Attendance.AttendanceType.PORTAL);
        attendance1.setTime(date);
        attendance1.setCourseCode("MATH101");
    }
}