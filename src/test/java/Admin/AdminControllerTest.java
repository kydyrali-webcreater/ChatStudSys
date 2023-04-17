package Admin;

import org.example.controller.AdminController;
import org.example.model.Dto.Student;
import org.example.model.Subject;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.SubjectRepository;
import org.example.service.SubjectService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private AdminController adminController;

    @Test
    public void testCreateSubject() {
        Subject subject = new Subject();

        Mockito.when(subjectRepository.save(subject)).thenReturn(subject);

        adminController.create(subject);

        Mockito.verify(subjectRepository).save(subject);
    }

    @Test
    public void testGetListOfSubject() {
        String studentId = "123";
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject());
        subjects.add(new Subject());

        Mockito.when(subjectService.getList(studentId)).thenReturn(subjects);

        List<Subject> result = adminController.getListOfSubject(studentId);

        Assert.assertEquals(subjects, result);
        Mockito.verify(subjectService).getList(studentId);
    }

    @Test
    public void testGetListOfStudent() {
        String subjectCode = "Math";
        List<Student> students = new ArrayList<>();

        Mockito.when(subjectService.getStudentList(subjectCode)).thenReturn(students);

        List<Student> result = adminController.getListOfStudent(subjectCode);

        Assert.assertEquals(students, result);
        Mockito.verify(subjectService).getStudentList(subjectCode);
    }

    @Test
    public void testChangeAttendance() {
        String studentId = "123";
        Long attendanceId = 1L;
        boolean isEnable = true;

        adminController.changeAttendance(studentId, attendanceId, isEnable);

        Mockito.verify(attendanceRepository).updateAttendanceStatus(attendanceId, isEnable);
    }

}
