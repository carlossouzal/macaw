package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.entity.ScheduleAssignment;
import edu.maplewood.master_schedule.repository.ScheduleAssignmentRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleAssignmentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleAssignmentService.class);
  private final ScheduleAssignmentRepository repository;

  @Autowired
  public ScheduleAssignmentService(ScheduleAssignmentRepository repository) {
    this.repository = repository;
  }

  public List<ScheduleAssignment> findAll() {
    LOGGER.debug("Retrieving all schedule assignments from the repository");
    return repository.findAll();
  }

  public ScheduleAssignment assign(ScheduleAssignment assignment) {
    LOGGER.debug("Assigning schedule: {}", assignment);
    return repository.save(assignment);
  }
}
