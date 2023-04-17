package Admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.model.Attendance;
import org.example.model.Dto.Student;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.UserRepository;
import org.example.service.impl.SubjectServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SubjectServiceTest {

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetList() {
        String studentId = "123";
        List<Subject> expectedSubjects = new ArrayList<>();
        expectedSubjects.add(new Subject());
        expectedSubjects.add(new Subject());
        when(subjectRepository.getListByStudentId(studentId)).thenReturn(expectedSubjects);

        List<Subject> actualSubjects = subjectService.getList(studentId);

        assertEquals(expectedSubjects, actualSubjects);
    }

    @Test
    public void testGetStudentList() {
        String subjectCode = "ENG101";
        String studentId = "123";
        User user = new User();
        Attendance attendance1 = new Attendance();
        attendance1.setAttendance(true);
        Attendance attendance2 = new Attendance();
        attendance2.setAttendance(false);
        List<Attendance> attendanceList = new ArrayList<>();
        attendanceList.add(attendance1);
        attendanceList.add(attendance2);

        when(subjectRepository.getListByCourseCode(subjectCode)).thenReturn("123,456");
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(attendanceRepository.getAttendanceByCourseCode(studentId, subjectCode)).thenReturn(attendanceList);

        List<Student> actualStudents = subjectService.getStudentList(subjectCode);

        assertNotNull(actualStudents);
        assertEquals(2, actualStudents.size());
        assertEquals(2, actualStudents.get(0).getAttendanceList().size());
        assertEquals(1, actualStudents.get(0).getNumAttendance());
        assertEquals(1, actualStudents.get(0).getNumAbsence());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testGetStudentList_UserNotFound() {
        String subjectCode = "ENG101";
        String studentId = "123";

        when(subjectRepository.getListByCourseCode(subjectCode)).thenReturn("123");
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.empty());

        subjectService.getStudentList(subjectCode);
    }
}
