package edu.maplewood.master_schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
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

public class CourseSectionServiceTest {

  private CourseSectionRepository repository;
  private CourseSectionService service;
  private SemesterService semesterService;
  private ClassroomService classroomService;

  @BeforeEach
  public void setUp() {
    repository = mock(CourseSectionRepository.class);
    semesterService = mock(SemesterService.class);
    classroomService = mock(ClassroomService.class);
    service = new CourseSectionService(repository, semesterService, classroomService);
  }

  @Test
  public void testFindAll() {
    CourseSection course = createCourseSection();
    when(repository.findAll()).thenReturn(List.of(course));

    List<CourseSection> courses = service.findAll();

    verify(repository).findAll();

    assertThat(courses).containsExactly(course);
  }

  public static CourseSection createCourseSection() {
    Course course = mock(Course.class);
    Semester semester = mock(Semester.class);

    CourseSection courseSection = new CourseSection();
    courseSection.setId(1L);
    courseSection.setCode("Section-1");
    courseSection.setCourse(course);
    courseSection.setSemester(semester);
    return courseSection;
  }
}
