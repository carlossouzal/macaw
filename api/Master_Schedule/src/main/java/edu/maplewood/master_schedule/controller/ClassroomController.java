package edu.maplewood.master_schedule.controller;

import edu.maplewood.master_schedule.config.Constants;
import edu.maplewood.master_schedule.controller.dto.response.ClassroomResponse;
import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.helper.mapper.ClassroomMapper;
import edu.maplewood.master_schedule.controller.parameters.ClassroomCriteria;
import edu.maplewood.master_schedule.entity.Classroom;
import edu.maplewood.master_schedule.service.ClassroomService;
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
@RequestMapping(Constants.CLASSROOM)
@Tag(name = "Classroom", description = "Classroom management endpoints")
public class ClassroomController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClassroomController.class);
  private final ClassroomService classroomService;

  public ClassroomController(ClassroomService classroomService) {
    this.classroomService = classroomService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseList<ClassroomResponse> getClassroom(
      @Valid @ModelAttribute ClassroomCriteria criteria) {
    LOGGER.debug("Fetching all teachers");
    Page<Classroom> classrooms = classroomService.find(criteria);
    return ClassroomMapper.toResponse(classrooms);
  }
}
