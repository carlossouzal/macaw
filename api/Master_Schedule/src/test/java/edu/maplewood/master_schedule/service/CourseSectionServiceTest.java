package edu.maplewood.master_schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.CourseSection;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.repository.CourseSectionRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

public class CourseSectionServiceTest {

  private CourseSectionRepository repository;
  private CourseSectionService service;
  private SemesterService semesterService;

  @BeforeEach
  public void setUp() {
    repository = mock(CourseSectionRepository.class);
    semesterService = mock(SemesterService.class);
    service = new CourseSectionService(repository, semesterService);
  }

  @Test
  public void testFindAll() {
    CourseSection course = mock(CourseSection.class);
    when(repository.findAll()).thenReturn(List.of(course));

    List<CourseSection> courses = service.findAll();

    verify(repository).findAll();

    assertEquals(courses.size(), 1);
    assertThat(courses).containsExactly(course);
  }

  @Test
  public void testFindAllEmpty() {
    when(repository.findAll()).thenReturn(List.of());

    List<CourseSection> courses = service.findAll();

    verify(repository).findAll();

    assertEquals(courses.size(), 0);
    assertThat(courses).containsExactly();
  }

  @Test
  void testCreateSection() {
    Course course = mock(Course.class);
    Semester semester = mock(Semester.class);

    when(course.getName()).thenReturn("Test Course");
    when(course.getCode()).thenReturn("CS101");
    when(semester.getName()).thenReturn("Fall 2024");
    when(repository.count(any(Specification.class))).thenReturn(1L);

    CourseSection section = service.createSection(course, semester);

    assertThat(section.getCourse()).isEqualTo(course);
    assertThat(section.getSemester()).isEqualTo(semester);
    verify(repository).save(any(CourseSection.class));
  }

  @Test
  void testCreateSectionNullCourse() {
    Semester semester = mock(Semester.class);

    when(semester.getName()).thenReturn("Fall 2024");
    when(repository.count(any(Specification.class))).thenReturn(1L);

    assertThrows(NullPointerException.class, () -> service.createSection(null, semester));
  }

  @Test
  void testCreateSectionNullSemester() {
    Course course = mock(Course.class);

    when(course.getName()).thenReturn("Test Course");
    when(course.getCode()).thenReturn("CS101");
    when(repository.count(any(Specification.class))).thenReturn(1L);

    assertThrows(NullPointerException.class, () -> service.createSection(course, null));
  }

  @Test
  void testCreateSectionForActiveSemester() {
    Semester semester = mock(Semester.class);
    Course course = mock(Course.class);
    when(semesterService.getActiveSemester()).thenReturn(semester);
    when(course.getName()).thenReturn("Test Course");
    when(course.getCode()).thenReturn("CS101");
    when(semester.getName()).thenReturn("Fall 2024");
    when(repository.count(any(Specification.class))).thenReturn(1L);

    CourseSection section = service.createSectionForActiveSemester(course);

    assertThat(section.getCourse()).isEqualTo(course);
    assertThat(section.getSemester()).isEqualTo(semester);
    verify(repository).save(any(CourseSection.class));
  }

  @Test
  void testCreateSectionForActiveSemesterCourseNull() {
    Semester semester = mock(Semester.class);
    Course course = null;
    when(semesterService.getActiveSemester()).thenReturn(semester);
    when(semester.getName()).thenReturn("Fall 2024");
    when(repository.count(any(Specification.class))).thenReturn(1L);

    assertThrows(NullPointerException.class, () -> service.createSectionForActiveSemester(course));
  }

  @Test
  void testCreateSectionForUnexistentSemester() {
    Semester semester = mock(Semester.class);
    Course course = mock(Course.class);
    when(semesterService.getActiveSemester()).thenReturn(null);
    when(semester.getName()).thenReturn("Fall 2024");
    when(repository.count(any(Specification.class))).thenReturn(1L);

    assertThrows(NullPointerException.class, () -> service.createSectionForActiveSemester(course));
  }

  @Test
  void testUpdateSection() {
    CourseSection courseSection = mock(CourseSection.class);
    when(courseSection.getCode()).thenReturn("CS101-001");

    service.updateSection(courseSection);

    verify(repository).save(courseSection);
  }

  @Test
  void testUpdateSectionNull() {
    CourseSection courseSection = mock(CourseSection.class);
    when(courseSection.getCode()).thenReturn("CS101-001");

    assertThrows(NullPointerException.class, () -> service.updateSection(null));
  }

  @Test
  void testSectionBySemester() {
    Semester semester = mock(Semester.class);
    CourseSection courseSection = mock(CourseSection.class);
    when(semester.getName()).thenReturn("Fall 2024");
    when(repository.findAll(any(Specification.class))).thenReturn(List.of(courseSection));

    List<CourseSection> sections = service.sectionBySemester(semester);

    verify(repository).findAll(any(Specification.class));

    assertEquals(sections.size(), 1);
    assertThat(sections).containsExactly(courseSection);
  }

  @Test
  void testSectionBySemesterEmpty() {
    Semester semester = mock(Semester.class);
    when(semester.getName()).thenReturn("Fall 2024");
    when(repository.findAll(any(Specification.class))).thenReturn(List.of());

    List<CourseSection> sections = service.sectionBySemester(semester);

    verify(repository).findAll(any(Specification.class));

    assertEquals(sections.size(), 0);
  }

  @Test
  void testSectionBySemesterNUll() {
    CourseSection courseSection = mock(CourseSection.class);
    when(repository.findAll(any(Specification.class))).thenReturn(List.of(courseSection));

    assertThrows(NullPointerException.class, () -> service.sectionBySemester(null));
  }
}
