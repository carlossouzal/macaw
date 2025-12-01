package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.controller.parameters.CourseSectionCriteria;
import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.CourseSection;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.entity.Teacher;
import edu.maplewood.master_schedule.repository.CourseSectionRepository;
import edu.maplewood.master_schedule.repository.specification.CourseSectionSpecification;
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
public class CourseSectionService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CourseSectionService.class);
  private final CourseSectionRepository courseSectionRepository;
  private final SemesterService semesterService;
  private final CourseService courseService;
  private final TeacherService teacherService;


  @Autowired
  public CourseSectionService(
      CourseSectionRepository courseSectionRepository,
      SemesterService semesterService, CourseService courseService,
      TeacherService teacherService) {
    this.semesterService = semesterService;
    this.courseSectionRepository = courseSectionRepository;
    this.courseService = courseService;
    this.teacherService = teacherService;
  }

  public List<CourseSection> findAll() {
    LOGGER.debug("Retrieving all course sections from the repository");
    return courseSectionRepository.findAll();
  }

  public Page<CourseSection> find(CourseSectionCriteria criteria) {
    LOGGER.debug("Retrieving Course Section from the repository with criteria {}", criteria);

    Course course = getCourse(criteria.courseId());
    Semester semester = getSemester(criteria.semesterId());
    Teacher teacher = getTeacher(criteria.teacherId());

    Specification<CourseSection> specs = CourseSectionSpecification.builder()
        .code(criteria.code())
        .course(course)
        .semester(semester)
        .teacher(teacher)
        .build();

    Pageable pageable = PageRequest.of(criteria.page(), criteria.size(),
        criteria.sortDirection().equals("ASC") ?
            Sort.by(criteria.sortBy()).ascending() :
            Sort.by(criteria.sortBy()).descending()
    );

    return courseSectionRepository.findAll(specs, pageable);
  }

  private Course getCourse(Long id) {
    if (id == null) {
      return null;
    }

    return courseService.findById(id);
  }

  private Semester getSemester(Long id) {
    if (id == null) {
      return null;
    }

    return semesterService.findById(id);
  }

  private Teacher getTeacher(Long id) {
    if (id == null) {
      return null;
    }

    return teacherService.findById(id);
  }

  public CourseSection createSection(Course course, Semester semester) {
    LOGGER.debug("Creating course section for course: {} in semester: {}", course.getName(),
        semester.getName());
    CourseSection section = new CourseSection();
    section.setCode("Section -" + sectionNumber(course, semester));
    section.setCourse(course);
    section.setSemester(semester);

    courseSectionRepository.saveAndFlush(section);
    
    LOGGER.info("Created course section: {} for course: {} in semester: {}", section.getCode(),
        course.getName(), semester.getName());
    return section;
  }

  public CourseSection createSectionForActiveSemester(Course course) {
    Semester activeSemester = semesterService.getActiveSemester();
    return createSection(course, activeSemester);
  }

  public CourseSection updateSection(CourseSection courseSection) {
    LOGGER.debug("Updating course section: {}", courseSection.getCode());
    return courseSectionRepository.save(courseSection);
  }


  public List<CourseSection> sectionBySemester(Semester semester) {
    LOGGER.debug("Counting course sections for semester: {}", semester.getName());
    Specification<CourseSection> specification = CourseSectionSpecification.builder()
        .semester(semester)
        .build();
    return courseSectionRepository.findAll(specification);
  }

  private String sectionNumber(Course course, Semester semester) {
    Specification<CourseSection> specification = CourseSectionSpecification.builder()
        .semester(semester)
        .course(course)
        .build();
    long count = courseSectionRepository.count(specification);
    return String.format("%03d", count + 1);
  }
}
