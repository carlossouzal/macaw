package edu.maplewood.master_schedule.controller.helper.mapper;

import edu.maplewood.master_schedule.controller.dto.response.CourseResponse;
import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.dto.response.SpecializationResponse;
import edu.maplewood.master_schedule.entity.Course;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

public class CourseMapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(CourseMapper.class);

  public static CourseResponse toResponse(Course course) {
    LOGGER.debug("Converting course to DTO");
    SpecializationResponse specialization = null;
    CourseResponse prerequisite = null;
    if (course.getSpecialization() != null) {
      specialization = SpecializationMapper.toResponse(course.getSpecialization());
    }
    if (course.getPrerequisite() != null) {
      prerequisite = CourseMapper.toResponse(course.getPrerequisite());
    }

    return new CourseResponse(
        course.getId(),
        course.getCode(),
        course.getName(),
        course.getDescription(),
        course.getCredits(),
        course.getHoursPerWeek(),
        course.getCourseType(),
        course.getGradeLevelMin(),
        course.getGradeLevelMax(),
        course.getSemesterOrder(),
        course.getCreatedAt(),
        specialization,
        prerequisite
    );
  }

  public static List<CourseResponse> toResponse(List<Course> courses) {
    LOGGER.debug("Converting course List to DTO");
    return courses.stream().map(CourseMapper::toResponse).toList();
  }

  public static ResponseList<CourseResponse> toResponse(Page<Course> courses) {
    LOGGER.debug(
        "Mapping Page of Courses entities to ResponseList<CourseResponse>. Total teachers: {}",
        courses.getTotalElements());
    List<Course> courseList = courses.getContent();
    List<CourseResponse> courseResponses = toResponse(courseList);

    return new ResponseList<>(courses.getTotalElements(),
        courses.getNumber(), courses.getSize(), courseResponses);
  }
}
