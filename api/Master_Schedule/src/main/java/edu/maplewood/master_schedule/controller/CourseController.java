package edu.maplewood.master_schedule.controller;

import edu.maplewood.master_schedule.config.Constants;
import edu.maplewood.master_schedule.controller.dto.response.CourseResponse;
import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.helper.mapper.CourseMapper;
import edu.maplewood.master_schedule.controller.parameters.CourseCriteria;
import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.COURSE)
@Tag(name = "Course", description = "Course management endpoints")
public class CourseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
  private final CourseService courseService;

  @Autowired
  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseList<CourseResponse> getCourses(
      @Valid @ModelAttribute CourseCriteria criteria) {
    LOGGER.debug("Fetching all teachers");
    Page<Course> courses = courseService.find(criteria);
    return CourseMapper.toResponse(courses);
  }
}
