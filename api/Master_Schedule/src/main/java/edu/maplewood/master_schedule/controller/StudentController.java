package edu.maplewood.master_schedule.controller;

import edu.maplewood.master_schedule.config.Constants;
import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.dto.response.StudentResponse;
import edu.maplewood.master_schedule.controller.helper.mapper.StudentMapper;
import edu.maplewood.master_schedule.controller.parameters.StudentsCriteria;
import edu.maplewood.master_schedule.entity.Student;
import edu.maplewood.master_schedule.service.StudentService;
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
@RequestMapping(Constants.STUDENTS)
@Tag(name = "Student", description = "Student management endpoints")
public class StudentController {

  private final static Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
  private final StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public ResponseList<StudentResponse> getStudents(
      @Valid @ModelAttribute StudentsCriteria criteria) {
    LOGGER.debug("Fetching all students");
    Page<Student> studentsPage = service.find(criteria);
    return StudentMapper.toResponse(studentsPage);
  }
}
