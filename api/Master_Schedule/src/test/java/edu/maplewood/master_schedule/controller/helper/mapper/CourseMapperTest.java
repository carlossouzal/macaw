package edu.maplewood.master_schedule.controller.helper.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import edu.maplewood.master_schedule.controller.dto.response.CourseResponse;
import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.dto.response.SpecializationResponse;
import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.Course.CourseType;
import edu.maplewood.master_schedule.entity.Semester.OrderInYear;
import edu.maplewood.master_schedule.entity.Specialization;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class CourseMapperTest {

  private Course createCourse(Long id) {
    Course c = new Course();
    c.setId(id);
    c.setCode("code");
    c.setName("name");
    c.setDescription("description");
    c.setHoursPerWeek(1);
    c.setCourseType(CourseType.CORE);
    c.setGradeLevelMax(12);
    c.setGradeLevelMin(9);
    c.setSemesterOrder(OrderInYear.FALL);
    return c;
  }

  @Test
  void toResponseSingleCourseMapsAllFields() {
    Course prerequisiteCourse = createCourse(2L);
    Specialization specialization = mock(Specialization.class);
    Course course = createCourse(1L);
    course.setPrerequisite(prerequisiteCourse);
    course.setSpecialization(specialization);
    SpecializationResponse specializationResponse = mock(SpecializationResponse.class);

    try (MockedStatic<SpecializationMapper> mapper = mockStatic(SpecializationMapper.class)) {
      mapper.when(() -> SpecializationMapper.toResponse(specialization))
          .thenReturn(specializationResponse);

      CourseResponse dto = CourseMapper.toResponse(course);

      assertEquals(1L, dto.id());
      assertEquals("code", dto.code());
      assertEquals("name", dto.name());
      assertEquals("description", dto.description());
      assertEquals(1, dto.hoursPerWeek());
      assertEquals(12, dto.gradeLevelMax());
      assertEquals(9, dto.gradeLevelMin());
      assertEquals(CourseType.CORE, dto.courseType());
      assertEquals(OrderInYear.FALL, dto.semesterOrder());
      assertEquals(dto.specialization(), specializationResponse);
      assertEquals(dto.prerequisite().id(), prerequisiteCourse.getId());
    }
  }

  @Test
  void toResponseSingleCourseMapsWithoutSpecialization() {
    Course prerequisiteCourse = createCourse(2L);
    Course course = createCourse(1L);
    course.setPrerequisite(prerequisiteCourse);

    CourseResponse dto = CourseMapper.toResponse(course);

    assertEquals(1L, dto.id());
    assertEquals("code", dto.code());
    assertEquals("name", dto.name());
    assertEquals("description", dto.description());
    assertEquals(1, dto.hoursPerWeek());
    assertEquals(12, dto.gradeLevelMax());
    assertEquals(9, dto.gradeLevelMin());
    assertEquals(CourseType.CORE, dto.courseType());
    assertEquals(OrderInYear.FALL, dto.semesterOrder());
    assertEquals(dto.prerequisite().id(), prerequisiteCourse.getId());
    assertNull(dto.specialization());
  }

  @Test
  void toResponseSingleCourseMapsWithoutPrerequisite() {
    Specialization specialization = mock(Specialization.class);
    Course course = createCourse(1L);
    course.setSpecialization(specialization);
    SpecializationResponse specializationResponse = mock(SpecializationResponse.class);

    try (MockedStatic<SpecializationMapper> mapper = mockStatic(SpecializationMapper.class)) {
      mapper.when(() -> SpecializationMapper.toResponse(specialization))
          .thenReturn(specializationResponse);

      CourseResponse dto = CourseMapper.toResponse(course);

      assertEquals(1L, dto.id());
      assertEquals("code", dto.code());
      assertEquals("name", dto.name());
      assertEquals("description", dto.description());
      assertEquals(1, dto.hoursPerWeek());
      assertEquals(12, dto.gradeLevelMax());
      assertEquals(9, dto.gradeLevelMin());
      assertEquals(CourseType.CORE, dto.courseType());
      assertEquals(OrderInYear.FALL, dto.semesterOrder());
      assertEquals(dto.specialization(), specializationResponse);
      assertNull(dto.prerequisite());
    }
  }

  @Test
  void toResponseSingleCourseMapsWithoutPrerequisiteAndSpecialization() {
    Course course = createCourse(1L);

    CourseResponse dto = CourseMapper.toResponse(course);

    assertEquals(1L, dto.id());
    assertEquals("code", dto.code());
    assertEquals("name", dto.name());
    assertEquals("description", dto.description());
    assertEquals(1, dto.hoursPerWeek());
    assertEquals(12, dto.gradeLevelMax());
    assertEquals(9, dto.gradeLevelMin());
    assertEquals(CourseType.CORE, dto.courseType());
    assertEquals(OrderInYear.FALL, dto.semesterOrder());
    assertNull(dto.specialization());
    assertNull(dto.prerequisite());
  }

  @Test
  void toResponseListMapsEachItem() {
    List<Course> entities = List.of(createCourse(1L), createCourse(2L));

    List<CourseResponse> dtos = CourseMapper.toResponse(entities);

    assertEquals(2, dtos.size());
    assertEquals(1L, dtos.get(0).id());
    assertEquals(2L, dtos.get(1).id());
    assertEquals("name", dtos.get(0).name());
    assertEquals("name", dtos.get(1).name());
  }

  @Test
  void toResponsePageMapsContentAndPageMetadata() {
    List<Course> content = List.of(createCourse(10L), createCourse(11L),
        createCourse(12L));
    PageRequest pageable = PageRequest.of(1, 3);
    Page<Course> page = new PageImpl<>(content, pageable, 9);

    ResponseList<CourseResponse> responseList = CourseMapper.toResponse(page);

    assertEquals(9, responseList.total());
    assertEquals(1, responseList.page());
    assertEquals(3, responseList.size());
    assertEquals(3, responseList.items().size());
    assertEquals(10L, responseList.items().get(0).id());
    assertEquals(12L, responseList.items().get(2).id());
    assertEquals(11L, responseList.items().get(1).id());
  }
}
