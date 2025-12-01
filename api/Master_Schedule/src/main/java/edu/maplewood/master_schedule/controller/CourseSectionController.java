package edu.maplewood.master_schedule.controller;

import edu.maplewood.master_schedule.config.Constants;
import edu.maplewood.master_schedule.controller.dto.response.CourseSectionResponse;
import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.helper.mapper.CourseSectionMapper;
import edu.maplewood.master_schedule.controller.parameters.CourseSectionCriteria;
import edu.maplewood.master_schedule.entity.CourseSection;
import edu.maplewood.master_schedule.service.CourseSectionService;
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
@RequestMapping(Constants.COURSE_SECTION)
@Tag(name = "Course Section", description = "Course endpoints")
public class CourseSectionController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CourseSectionController.class);
  private final CourseSectionService courseSectionService;

  @Autowired
  public CourseSectionController(CourseSectionService courseSectionService) {
    this.courseSectionService = courseSectionService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseList<CourseSectionResponse> getCourseSection(
      @Valid @ModelAttribute CourseSectionCriteria criteria) {
    LOGGER.debug("Fetching all Course Sections");
    Page<CourseSection> courseSectionPage = courseSectionService.find(criteria);
    return CourseSectionMapper.toResponse(courseSectionPage);
  }
}
