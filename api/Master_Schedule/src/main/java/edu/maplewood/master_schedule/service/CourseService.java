package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.controller.parameters.CourseCriteria;
import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.Course.CourseType;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.entity.Semester.OrderInYear;
import edu.maplewood.master_schedule.entity.Specialization;
import edu.maplewood.master_schedule.repository.CourseRepository;
import edu.maplewood.master_schedule.repository.specification.CourseSpecification;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);
  private final CourseRepository courseRepository;
  private final SpecializationService specializationService;

  @Autowired
  public CourseService(CourseRepository courseRepository,
      SpecializationService specializationService) {
    this.courseRepository = courseRepository;
    this.specializationService = specializationService;
  }

  public List<Course> findAll() {
    LOGGER.debug("Retrieving all courses from the repository");
    return courseRepository.findAll();
  }

  public Page<Course> find(CourseCriteria criteria) {
    LOGGER.debug("Retrieving courses from the repository with criteria {}", criteria);
    Course prerequisite = getCourse(criteria.prerequisiteId());
    Specialization specialization = getSpecialization(criteria.specializationId());
    OrderInYear semesterOrder = getSemesterOrder(criteria.semesterOrder());
    CourseType courseType = getCourseType(criteria.courseType());

    Specification<Course> specs = CourseSpecification.builder()
        .code(criteria.code())
        .name(criteria.name())
        .description(criteria.description())
        .creditsMin(criteria.creditsMin())
        .creditsMax(criteria.creditsMax())
        .hoursPerWeekMin(criteria.hoursPerWeekMin())
        .hoursPerWeekMax(criteria.hoursPerWeekMax())
        .courseType(courseType)
        .gradeLevelMin(criteria.gradeLevelMin())
        .gradeLevelMax(criteria.gradeLevelMax())
        .semesterOrder(semesterOrder)
        .specialization(specialization)
        .prerequisite(prerequisite)
        .build();

    Pageable pageable = PageRequest.of(criteria.page(), criteria.size(),
        criteria.sortDirection().equals("ASC") ?
            Sort.by(criteria.sortBy()).ascending() :
            Sort.by(criteria.sortBy()).descending()
    );

    return courseRepository.findAll(specs, pageable);
  }

  private Course getCourse(Long id) {
    if (id == null) {
      return null;
    }
    return findById(id);
  }

  private Specialization getSpecialization(Long id) {
    if (id == null) {
      return null;
    }

    return specializationService.findById(id);
  }

  private OrderInYear getSemesterOrder(String semesterOrder) {
    if (semesterOrder == null) {
      return null;
    }

    return Enum.valueOf(OrderInYear.class, semesterOrder);
  }

  private CourseType getCourseType(String code) {
    if (code == null) {
      return null;
    }

    return Enum.valueOf(CourseType.class, code);
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
