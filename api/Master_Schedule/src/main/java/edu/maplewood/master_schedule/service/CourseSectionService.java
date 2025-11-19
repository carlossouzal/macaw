package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.CourseSection;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.repository.CourseSectionRepository;
import edu.maplewood.master_schedule.repository.specification.CourseSectionSpecification;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CourseSectionService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CourseSectionService.class);
  private final CourseSectionRepository courseSectionRepository;
  private final SemesterService semesterService;
  private final ClassroomService classroomService;


  @Autowired
  public CourseSectionService(
      CourseSectionRepository courseSectionRepository,
      SemesterService semesterService,
      ClassroomService classroomService) {
    this.semesterService = semesterService;
    this.courseSectionRepository = courseSectionRepository;
    this.classroomService = classroomService;
  }

  public List<CourseSection> findAll() {
    LOGGER.debug("Retrieving all course sections from the repository");
    return courseSectionRepository.findAll();
  }

  public CourseSection createSection(Course course, Semester semester) {
    LOGGER.debug("Creating course section for course: {} in semester: {}", course.getName(),
        semester.getName());
    CourseSection section = new CourseSection();
    section.setCode("Section -" + sectionNumber(course, semester));
    section.setCourse(course);
    section.setSemester(semester);

    courseSectionRepository.save(section);

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
