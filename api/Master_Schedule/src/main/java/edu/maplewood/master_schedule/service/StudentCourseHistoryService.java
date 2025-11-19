package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.entity.StudentCourseHistory;
import edu.maplewood.master_schedule.repository.StudentCourseHistoryRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseHistoryService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StudentCourseHistoryService.class);
  private final StudentCourseHistoryRepository repository;

  @Autowired
  public StudentCourseHistoryService(StudentCourseHistoryRepository repository) {
    this.repository = repository;
  }

  public List<StudentCourseHistory> findAll() {
    LOGGER.debug("Retrieving all student course histories from the repository");
    return repository.findAll();
  }
}
