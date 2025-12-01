package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.controller.parameters.StudentsCriteria;
import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.CourseSection;
import edu.maplewood.master_schedule.entity.Student;
import edu.maplewood.master_schedule.entity.Student.StudentStatus;
import edu.maplewood.master_schedule.exception.NoAvailableResource;
import edu.maplewood.master_schedule.repository.StudentRepository;
import edu.maplewood.master_schedule.repository.specification.StudentSpecification;
import jakarta.transaction.Transactional;
import java.util.List;
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
public class StudentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);
  private final StudentRepository repository;
  private final CourseSectionService courseSectionService;
  private final CourseService courseService;

  @Autowired
  public StudentService(StudentRepository repository, CourseSectionService courseSectionService,
      CourseService courseService) {
    this.courseSectionService = courseSectionService;
    this.repository = repository;
    this.courseService = courseService;
  }

  public List<Student> findAll() {
    LOGGER.debug("Retrieving all students from the repository");
    return repository.findAll();
  }

  public Page<Student> find(StudentsCriteria criteria) {
    LOGGER.debug("Fetching all students from the repository");
    Course prerequired = null;
    if (criteria.prerequiredCourseId() != null) {
      prerequired = courseService.findById(criteria.prerequiredCourseId());
    }
    StudentStatus status = null;
    if (criteria.status() != null) {
      status = Enum.valueOf(StudentStatus.class, criteria.status());
    }

    Specification<Student> specs = StudentSpecification.builder()
        .name(criteria.name())
        .email(criteria.email())
        .gradeLevelBetween(criteria.gradeLevelMin(), criteria.gradeLevelMax())
        .enrollmentYearBetween(criteria.enrollmentYearMin(), criteria.enrollmentYearMax())
        .expectedGraduationYearBetween(criteria.expectedGraduationYearMin(),
            criteria.expectedGraduationYearMax())
        .prerequiredCourse(prerequired)
        .status(status)
        .build();

    Pageable pageable = PageRequest.of(criteria.page(), criteria.size(),
        criteria.sortDirection().equals("ASC") ?
            Sort.by(criteria.sortBy()).ascending() :
            Sort.by(criteria.sortBy()).descending()
    );

    return repository.findAll(specs, pageable);
  }

  public long getNumberOfStudentsThatCanEnroll(Course course) {
    LOGGER.debug("Calculating number of students that can enroll in course: {}", course.getName());
    Specification<Student> specs = StudentSpecification.builder()
        .gradeLevelBetween(course.getGradeLevelMin(), course.getGradeLevelMax())
        .prerequiredCourse(course.getPrerequisite())
        .status(StudentStatus.ACTIVE)
        .build();

    return repository.count(specs);
  }

  @Transactional
  public Student enrollToCourse(Course course, Student student) {
    LOGGER.info("Enrolling student {} to course {}", student.getId(), course.getName());
    long enrolledOnce = student.getCourseSectionsEnrolled().stream()
        .filter(section -> section.getSemester().getIsActive())
        .map(CourseSection::getCourse)
        .filter(c -> c.equals(course))
        .count();
    if (enrolledOnce > 0) {
      LOGGER.warn("Student {} has already enrolled in course {}", student.getId(),
          course.getName());
      throw new IllegalStateException("Student has already enrolled in this course.");
    }

    CourseSection courseWithSpots = course.getCourseSections().stream()
        .filter(courseSection -> courseSection.getStudents().size() < 10)
        .findFirst()
        .orElseThrow(() -> new NoAvailableResource("No enrolled in course " + course.getName()));

    student.getCourseSectionsEnrolled().add(courseWithSpots);
    if (courseWithSpots.getStudents().size() >= 10) {
      try {
        createNewCourseSection(courseWithSpots.getCourse());
      } catch (NoAvailableResource noAvailableResource) {
        LOGGER.warn("No more sections available for course {}", course.getName());
      }
    }
    return repository.save(student);
  }

  private CourseSection createNewCourseSection(Course course) {
    LOGGER.info("Creating new course section for course: {}", course.getName());

    return courseSectionService.createSectionForActiveSemester(course);
  }
}
