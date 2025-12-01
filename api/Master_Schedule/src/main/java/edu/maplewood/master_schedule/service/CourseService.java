package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);
  private final CourseRepository courseRepository;

  @Autowired
  public CourseService(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  public List<Course> findAll() {
    LOGGER.debug("Retrieving all courses from the repository");
    return courseRepository.findAll();
  }

  public Course findById(Long id) {
    LOGGER.debug("Retrieving course with id {}", id);
    Optional<Course> course = courseRepository.findById(id);
    if (course.isEmpty()) {
      throw new EntityNotFoundException("Course with id " + id + " not found");
    }

    return course.get();
  }

  public List<Course> findBySemesterOrder(Semester.OrderInYear order) {
    LOGGER.debug("Finding courses for semester order: {}", order);
    return courseRepository.findBySemesterOrder(order);
  }
}
