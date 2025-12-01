package edu.maplewood.master_schedule.controller;

import edu.maplewood.master_schedule.config.Constants;
import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.dto.response.TeacherResponse;
import edu.maplewood.master_schedule.controller.helper.mapper.TeacherMapper;
import edu.maplewood.master_schedule.controller.parameters.TeacherCriteria;
import edu.maplewood.master_schedule.entity.Teacher;
import edu.maplewood.master_schedule.service.TeacherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.TEACHERS)
@Tag(name = "Teacher", description = "Teacher management endpoints")
public class TeacherController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);
  private TeacherService teacherService;

  public TeacherController(TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseList<TeacherResponse> getSemesters(
      @Valid @ModelAttribute TeacherCriteria criteria) {
    LOGGER.debug("Fetching all teachers");
    Page<Teacher> teacherPage = teacherService.find(criteria);
    return TeacherMapper.toResponse(teacherPage);
  }
}
