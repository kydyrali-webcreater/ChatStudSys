//package Teacher;
//
//import static org.mockito.Mockito.*;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//import org.example.Exceptions.models.BasicException;
//import org.example.model.Attendance;
//import org.example.model.Dto.Student;
//import org.example.model.Dto.StudentAttendance;
//import org.example.model.User;
//import org.example.service.impl.TeacherServiceImpl;
//import org.example.repository.AttendanceRepository;
//import org.example.repository.SubjectRepository;
//import org.example.repository.UserRepository;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//public class TeacherServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private SubjectRepository subjectRepository;
//
//    @Mock
//    private AttendanceRepository attendanceRepository;
//
//    @InjectMocks
//    private TeacherServiceImpl teacherService;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
////    @Test
//    public void testGetListStudents() {
//        String studentId = "123";
//        String teacherId = "456";
//        String studentIds = studentId + ",789";
//        List<String> studentIdsList = Arrays.asList(studentIds.split(","));
//
//        // Mocking the subjectRepository
//        when(subjectRepository.getListStudentsByTeacherId(teacherId)).thenReturn(studentIds);
//
//        // Mocking the userRepository for the first student
//        User user1 = new User();
//        user1.setId(studentId);
//        when(userRepository.findByUserId(studentId)).thenReturn(Optional.of(user1));
//
//        // Mocking the userRepository for the second student
//        User user2 = new User();
//        user2.setId("789");
//        when(userRepository.findByUserId("789")).thenReturn(Optional.of(user2));
//
//        List<Student> students = teacherService.getListStudents(studentId, teacherId);
//    }
//
//    @Test(expected = BasicException.class)
//    public void testGetListStudentsNoStudents() {
//        String studentId = "123";
//        String teacherId = "456";
//        String studentIds = "789";
//        List<String> studentIdsList = Arrays.asList(studentIds.split(","));
//
//        // Mocking the subjectRepository
//        when(subjectRepository.getListStudentsByTeacherId(teacherId)).thenReturn(studentIds);
//
//        teacherService.getListStudents(studentId, teacherId);
//    }
//
//    public void testSetAttendance() {
//        User teacher = new User();
//        teacher.setId("123");
//
//        Set<StudentAttendance> studentAttendances = new HashSet<>();
//
//        StudentAttendance studentAttendance1 = new StudentAttendance();
//        studentAttendance1.setStudentId("456");
//        studentAttendance1.setAttendance(true);
//        studentAttendances.add(studentAttendance1);
//
//        // Mocking the subjectRepository
//        when(subjectRepository.getListStudentsByTeacherId(teacher.getId())).thenReturn("456,789");
//
//        Attendance attendance = new Attendance();
//        attendance.setAttendance(true);
//        attendance.setStudentId(studentAttendance1.getStudentId());
//        attendance.setAttendanceType(Attendance.AttendanceType.PORTAL);
//        attendance.setTime(LocalDateTime.now());
//        attendance.setCourseCode(subjectRepository.getCourseCodeByTeacherId(teacher.getId()));
//        attendance.setPutedById(teacher.getId());
//        attendance.setPutedByInfo(teacher.getLastname() + " " + teacher.getFirstname());
//        attendance.setPutedByRole(Attendance.PutedByRole.TEACHER);
//
//        // Mocking the attendanceRepository
//// Mocking the userRepository for the student
//        User user = new User();
//        user.setId(studentAttendance1.getStudentId());
//        when(userRepository.findByUserId(studentAttendance1.getStudentId())).thenReturn(Optional.of(user));
//        teacherService.setAttendance(teacher, studentAttendances);
//
//    }
//
//    @Test(expected = BasicException.class)
//    public void testSetAttendanceNoStudents() {
//        User teacher = new User();
//        teacher.setId("123");
//
//        Set<StudentAttendance> studentAttendances = new HashSet<>();
//
//        StudentAttendance studentAttendance1 = new StudentAttendance();
//        studentAttendance1.setStudentId("456");
//        studentAttendance1.setAttendance(true);
//        studentAttendances.add(studentAttendance1);
//
//        // Mocking the subjectRepository
//        when(subjectRepository.getListStudentsByTeacherId(teacher.getId())).thenReturn("789");
//
//        teacherService.setAttendance(teacher, studentAttendances);
//    }
//}