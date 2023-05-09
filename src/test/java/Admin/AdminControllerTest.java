package Admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.controller.AdminController;
import org.example.model.Attendance;
import org.example.model.Dto.Student;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.service.SubjectService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private SubjectService subjectService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateSubject() {
        Subject subject = new Subject();
        adminController.create(subject);
    }

    @Test
    public void testGetListOfSubject() {
        String studentId = "123";
        List<Subject> expectedSubjects = new ArrayList<>();
        expectedSubjects.add(new Subject());
        expectedSubjects.add(new Subject());
        when(subjectService.getList(studentId)).thenReturn(expectedSubjects);

        List<Subject> actualSubjects = adminController.getListOfSubject(studentId);

        assertEquals(expectedSubjects, actualSubjects);
    }

    @Test
    public void testGetListOfStudent() {
        String subjectCode = "ENG101";
        User user = new User();
        Attendance attendance1 = new Attendance();
        attendance1.setAttendance(true);
        Attendance attendance2 = new Attendance();
        attendance2.setAttendance(false);
        List<Attendance> attendanceList = new ArrayList<>();
        attendanceList.add(attendance1);
        attendanceList.add(attendance2);
        List<Student> expectedStudents = new ArrayList<>();

        when(subjectService.getStudentList(subjectCode)).thenReturn(expectedStudents);

        List<Student> actualStudents = adminController.getListOfStudent(subjectCode);

        assertNotNull(actualStudents);
        assertEquals(expectedStudents, actualStudents);
    }

//    @Test
//    public void testChangeAttendance() {
//        String studentId = "123";
//        Long attendanceId = 1L;
//        boolean isEnable = true;
//        adminController.changeAttendance(studentId, attendanceId, isEnable);
//    }

}
