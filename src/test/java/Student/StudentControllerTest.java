package Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.example.Exceptions.models.BasicException;
import org.example.controller.StudentController;
import org.example.model.Attendance;
import org.example.model.Dto.AttendanceListByCourseDto;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.example.service.StudentService;
import org.example.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.PathVariable;

class StudentControllerTest {

    @Mock
    private SubjectService subjectService;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private StudentController studentController;

    private User testStudent;
    private Subject testSubject;
    private Attendance testAttendance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testStudent = new User();
        testStudent.setId("test_student_id");
        testStudent.setFirstname("Test");
        testStudent.setLastname("Student");

        testSubject = new Subject();
        testSubject.setCourseCode("test_course_code");

        testAttendance = new Attendance();
        testAttendance.setCourseCode(testSubject.getCourseCode());
        testAttendance.setStudentId(testStudent.getId());
    }

    @Test
    @DisplayName("Get list of subjects for student")
    void testGetListOfSubjects() {
        // Arrange
        List<Subject> expectedSubjects = Arrays.asList(testSubject);
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(testStudent));
        when(subjectService.getList(testStudent.getId())).thenReturn(expectedSubjects);

        // Act
        List<Subject> actualSubjects = studentController.getListOfSubject(testStudent.getId());

        // Assert
        assertThat(actualSubjects).isEqualTo(expectedSubjects);
    }

    @Test
    @DisplayName("Take attendance for student")
    void testTakeAttendance() {
        // Arrange
        when(studentService.takeAttendance(anyString(), ArgumentMatchers.any(Attendance.class)))
                .thenReturn(testAttendance);

        // Act
        Attendance actualAttendance = studentController.takeAttendance(testStudent.getId(), testAttendance);

        // Assert
        assertThat(actualAttendance).isEqualTo(testAttendance);
    }

    @Test
    @DisplayName("Get attendance by course code for student")
    void testGetAttendanceByCourse() {
        // Arrange
        List<Attendance> expectedAttendance = Arrays.asList(testAttendance);
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(testStudent));
        when(attendanceRepository.getAttendanceByCourseCode(testStudent.getId(), testSubject.getCourseCode()))
                .thenReturn(expectedAttendance);

        // Act
        List<Attendance> actualAttendance = studentController.getAttendanceByCourse(
                testStudent.getId(), testSubject.getCourseCode());

        // Assert
        assertThat(actualAttendance).isEqualTo(expectedAttendance);
    }

    @Test
    @DisplayName("Get all attendance for student")
    void testGetAllAttendance() {
        // Arrange
        List<Attendance> expectedAttendance = Arrays.asList(testAttendance);
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(testStudent));
        when(attendanceRepository.getAttendanceByStudentId(testStudent.getId())).thenReturn(expectedAttendance);
        // Act
        List<AttendanceListByCourseDto> actualAttendance = studentController.getAllAttendance(testStudent.getId());

    }

    @Test
    @DisplayName("Get list of attendance by course for student")
    void testGetListOfAttendanceByCourse() {
        // Arrange
        List<Attendance> expectedAttendance = Arrays.asList(testAttendance);
        AttendanceListByCourseDto attendanceListByCourseDto = new AttendanceListByCourseDto();
        attendanceListByCourseDto.setCourseCode(testSubject.getCourseCode());
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(testStudent));
        when(subjectRepository.getListByCourseCode(testSubject.getCourseCode())).thenReturn(String.valueOf(Optional.of(testSubject)));
        // Act
        List<Attendance> actualAttendance = studentController.getAttendanceByCourse(
                testStudent.getId(), String.valueOf(attendanceListByCourseDto));

    }

    @Test
    @DisplayName("Get list of attendance by course with invalid date range for student")
    void testGetListOfAttendanceByCourseInvalidDate() {
        // Arrange
        AttendanceListByCourseDto attendanceListByCourseDto = new AttendanceListByCourseDto();
        attendanceListByCourseDto.setCourseCode(testSubject.getCourseCode());

    }
}
