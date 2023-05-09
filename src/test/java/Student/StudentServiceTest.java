package Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.example.Exceptions.models.BasicException;
import org.example.model.Attendance;
import org.example.model.Dto.AttendanceListByCourseDto;
import org.example.model.Dto.Student;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.example.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

public class StudentServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void takeAttendance_shouldThrowExceptionIfStudentNotFound() {
        // Arrange
        String studentId = "123";
        Attendance attendance = new Attendance();
        attendance.setStudentId(studentId);

        when(userRepository.findByUserId(studentId)).thenReturn(Optional.empty());

        // Act and assert
        assertThatThrownBy(() -> studentService.takeAttendance(studentId, attendance))
                .isInstanceOf(BasicException.class)
                .hasMessage("STUDENT NOT FOUND");
    }

    @Test
    void takeAttendance_shouldThrowExceptionIfCourseNotFound() {
        // Arrange
        String studentId = "123";
        Attendance attendance = new Attendance();
        attendance.setStudentId(studentId);
        attendance.setCourseCode("CSE101");
        attendance.setSubjectId(1L);

        when(userRepository.findByUserId(studentId)).thenReturn(Optional.of(new User()));
        when(subjectRepository.getByCourseCode(attendance.getCourseCode())).thenReturn(new ArrayList<Subject>());

        // Act and assert
        assertThatThrownBy(() -> studentService.takeAttendance(studentId, attendance))
                .isInstanceOf(BasicException.class)
                .hasMessage("NOT FOUND COURSE");
    }

    @Test
    void takeAttendance_shouldThrowExceptionIfAttendanceStudentIdDoesNotMatch() {
        // Arrange
        String studentId = "123";
        Attendance attendance = new Attendance();
        attendance.setStudentId("456");

        when(userRepository.findByUserId(studentId)).thenReturn(Optional.of(new User()));

    }

    @Test
    void takeAttendance_shouldThrowExceptionIfStudentDoesNotHaveTheCourse() {
        // Arrange
        String studentId = "123";
        Attendance attendance = new Attendance();
        attendance.setStudentId(studentId);
        attendance.setCourseCode("CSE101");
        attendance.setSubjectId(1L);

        when(userRepository.findByUserId(studentId)).thenReturn(Optional.of(new User()));
        when(subjectRepository.getByCourseCode(attendance.getCourseCode())).thenReturn(Arrays.asList(new Subject()));

        when(subjectRepository.getListByCourseId(attendance.getSubjectId())).thenReturn("456,789");

    }

//    @Test
//    void getAttendanceListByCourse_shouldReturnAttendanceListByCourse() {
//        // Arrange
//        String studentId = "123";
//        int page = 0;
//        int size = 10;
//        String courseCode = "CSE101";
//        List<Long> subjectIds = Arrays.asList(1L, 2L);
//
//        User student = new User();
//        student.setId(studentId);
//
//        when(userRepository.findByUserId(studentId)).thenReturn(Optional.of(student));
//
//        Subject subject1 = new Subject();
//        subject1.setId(1L);
//        subject1.setCourseCode(courseCode);
//        subject1.setTime(LocalTime.from(LocalDateTime.of(2023, 4, 26, 8, 30)));
//
//        Subject subject2 = new Subject();
//        subject2.setId(2L);
//        subject2.setCourseCode(courseCode);
//        subject2.setTime(LocalTime.from(LocalDateTime.of(2023, 4, 27, 8, 30)));
//
//        when(subjectRepository.getListByCourseCode(courseCode)).thenReturn(Arrays.asList(subject1, subject2).toString());
//
//        AttendanceListByCourseDto attendanceListByCourseDto1 = new AttendanceListByCourseDto();
//        attendanceListByCourseDto1.setCourseCode(String.valueOf(subject1.getId()));
//        attendanceListByCourseDto1.setCourseName("Subject 1");
//        attendanceListByCourseDto1.setAttendanceList(new ArrayList<>());
//    }
}