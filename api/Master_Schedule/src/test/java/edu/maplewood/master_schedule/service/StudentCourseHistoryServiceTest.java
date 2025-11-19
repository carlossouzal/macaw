package edu.maplewood.master_schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.entity.Student;
import edu.maplewood.master_schedule.entity.StudentCourseHistory;
import edu.maplewood.master_schedule.entity.StudentCourseHistory.StudentCourseStatus;
import edu.maplewood.master_schedule.repository.StudentCourseHistoryRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentCourseHistoryServiceTest {

  private StudentCourseHistoryRepository repository;
  private StudentCourseHistoryService service;

  @BeforeEach
  public void setUp() {
    repository = mock(StudentCourseHistoryRepository.class);
    service = new StudentCourseHistoryService(repository);
  }

  @Test
  public void testFindAll() {
    StudentCourseHistory studentCourseHistory = createStudentCourseHistory();
    when(repository.findAll()).thenReturn(List.of(studentCourseHistory));

    List<StudentCourseHistory> studentCourseHistories = service.findAll();

    verify(repository).findAll();

    assertThat(studentCourseHistories).containsExactly(studentCourseHistory);
  }

  public static StudentCourseHistory createStudentCourseHistory() {
    Student student = mock(Student.class);
    Course course = mock(Course.class);
    Semester semester = mock(Semester.class);
    StudentCourseHistory studentCourseHistory = new StudentCourseHistory();
    studentCourseHistory.setId(1L);
    studentCourseHistory.setStudent(student);
    studentCourseHistory.setCourse(course);
    studentCourseHistory.setSemester(semester);
    studentCourseHistory.setStatus(StudentCourseStatus.PASSED);
    return studentCourseHistory;
  }
}
