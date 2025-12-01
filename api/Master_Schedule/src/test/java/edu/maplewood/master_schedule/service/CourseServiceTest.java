package edu.maplewood.master_schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.Semester.OrderInYear;
import edu.maplewood.master_schedule.entity.Specialization;
import edu.maplewood.master_schedule.repository.CourseRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseServiceTest {

  private CourseRepository repository;
  private CourseService service;
  private SpecializationService specializationService;

  @BeforeEach
  public void setUp() {
    specializationService = mock(SpecializationService.class);
    repository = mock(CourseRepository.class);
    service = new CourseService(repository, specializationService);
  }

  @Test
  public void testFindAll() {
    Course course = createCourse();
    when(repository.findAll()).thenReturn(List.of(course));

    List<Course> courses = service.findAll();

    verify(repository).findAll();

    assertThat(courses).containsExactly(course);
  }

  public static Course createCourse() {
    Specialization specialization = mock(Specialization.class);
    Course course = new Course();
    course.setId(1L);
    course.setName("Course Name");
    course.setCode("code");
    course.setSemesterOrder(OrderInYear.FALL);
    course.setSpecialization(specialization);
    return course;
  }
}
