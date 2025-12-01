package edu.maplewood.master_schedule.controller;

import edu.maplewood.master_schedule.config.Constants;
import edu.maplewood.master_schedule.controller.dto.request.CreateSemesterRequest;
import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.dto.response.SemesterResponse;
import edu.maplewood.master_schedule.controller.helper.mapper.SemesterMapper;
import edu.maplewood.master_schedule.controller.parameters.SemesterCriteria;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.service.SemesterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.SEMESTERS)
@Tag(name = "Semester", description = "Semester management endpoints")
public class SemesterController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SemesterController.class);
  private final SemesterService semesterService;

  @Autowired
  public SemesterController(SemesterService semesterService) {
    this.semesterService = semesterService;
  }

  @GetMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public ResponseList<SemesterResponse> getSemesters(
      @Valid @ModelAttribute SemesterCriteria criteria) {
    LOGGER.debug("Fetching all semesters");
    Page<Semester> semesterPage = semesterService.find(criteria);
    return SemesterMapper.toResponse(semesterPage);
  }

  @PostMapping(produces = "application/json", consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public SemesterResponse createSemester(
      @Valid @RequestBody CreateSemesterRequest createSemesterRequest) {
    LOGGER.debug("Creating new semester with data: {}", createSemesterRequest);
    Semester semester = SemesterMapper.toEntity(createSemesterRequest);
    semesterService.create(semester);
    return SemesterMapper.toResponse(semester);
  }
}
