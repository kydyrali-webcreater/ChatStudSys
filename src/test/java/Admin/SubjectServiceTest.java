package Admin;

import org.example.model.Attendance;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Test
    public void testGetList() {
        // Arrange
        String studentId = "123";
        User user = new User();
        user.setId(studentId);
        user.setFirstname("John");
        user.setLastname("Doe");

        Subject subject1 = new Subject();
        subject1.setId(1L);
        subject1.setName("Math");
        subject1.setTeacherId(studentId);

        Subject subject2 = new Subject();
        subject2.setId(2L);
        subject2.setName("Science");
        subject2.setTeacherId(studentId);

        List<Subject> subjects = Arrays.asList(subject1, subject2);

    }

    @Test
    public void testUpdateAttendance() {
        // Arrange
        String studentId = "123";
        String subjectCode = "CSE101";
        Attendance attendanceUpdateRequest = new Attendance();
        attendanceUpdateRequest.setStudentId(studentId);
        attendanceUpdateRequest.setCourseCode(subjectCode);
        User user = new User();
        user.setId(studentId);
        user.setFirstname("John");
        user.setLastname("Doe");

        Subject subject = new Subject();
        subject.setId(1L);
        subject.setCourseCode(subjectCode);

        Attendance attendance = new Attendance();
        attendance.setStudentId(studentId);
        attendance.setSubjectId(1L);

    }

    @Test
    public void testUpdateAttendance_InvalidStudentId() {
        // Arrange
        String studentId = "123";
        String subjectCode = "CSE101";
        Attendance attendanceUpdateRequest = new Attendance();
        attendanceUpdateRequest.setStudentId(studentId);
        attendanceUpdateRequest.setCourseCode(subjectCode);
    }

    @Test
    public void testUpdateAttendance_InvalidSubjectCode() {
// Arrange
        String studentId = "123";
        String subjectCode = "CSE101";
        Attendance attendanceUpdateRequest = new Attendance();
        attendanceUpdateRequest.setStudentId(studentId);
        attendanceUpdateRequest.setCourseCode(subjectCode);
        User user = new User();
        user.setId(studentId);
    }

    @Test
    public void testUpdateAttendance_InvalidAttendance() {
// Arrange
        String studentId = "123";
        String subjectCode = "CSE101";
        Attendance attendanceUpdateRequest = new Attendance();
        attendanceUpdateRequest.setStudentId(studentId);
        attendanceUpdateRequest.setCourseCode(subjectCode);
        User user = new User();
        user.setId(studentId);

        Subject subject = new Subject();
        subject.setId(1L);
        subject.setCourseCode(subjectCode);

    }
}

