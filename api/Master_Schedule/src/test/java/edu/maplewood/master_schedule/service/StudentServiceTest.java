package edu.maplewood.master_schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.maplewood.master_schedule.entity.Student;
import edu.maplewood.master_schedule.repository.StudentRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentServiceTest {

  private StudentRepository repository;
  private StudentService service;
  private CourseSectionService courseSectionService;
  private CourseService courseService;

  @BeforeEach
  public void setUp() {
    repository = mock(StudentRepository.class);
    courseSectionService = mock(CourseSectionService.class);
    courseService = mock(CourseService.class);
    service = new StudentService(repository, courseSectionService, courseService);
  }

  @Test
  public void testFindAll() {
    Student student = createStudent();
    when(repository.findAll()).thenReturn(List.of(student));

    List<Student> students = service.findAll();

    verify(repository).findAll();

    assertThat(students).containsExactly(student);
  }

  public static Student createStudent() {
    Student student = new Student();
    student.setId(1L);
    student.setFirstName("John");
    student.setLastName("Doe");
    student.setGradeLevel(10);
    student.setEnrollmentYear(2022);
    return student;
  }
}
