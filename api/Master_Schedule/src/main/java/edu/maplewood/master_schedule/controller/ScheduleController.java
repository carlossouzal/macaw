package edu.maplewood.master_schedule.controller;


import edu.maplewood.master_schedule.config.Constants;
import edu.maplewood.master_schedule.controller.dto.request.ScheduleGenerateRequest;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.service.ScheduleService;
import edu.maplewood.master_schedule.service.SemesterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.SCHEDULES)
@Tag(name = "Schedule Controller", description = "Schedule endpoints")
public class ScheduleController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleController.class);
  private final ScheduleService scheduleService;
  private final SemesterService semesterService;

  @Autowired
  public ScheduleController(ScheduleService scheduleService, SemesterService semesterService) {
    this.scheduleService = scheduleService;
    this.semesterService = semesterService;
  }

  @PostMapping(produces = "application/json", consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public void createSchedule(@Valid @RequestBody ScheduleGenerateRequest scheduleGenerateRequest)
      throws EntityNotFoundException {
    LOGGER.debug("Creating a new schedule");
    Semester semester = semesterService.findById(scheduleGenerateRequest.semesterId());
    scheduleService.generateSchedule(semester);
    // Implementation goes here
  }
}
