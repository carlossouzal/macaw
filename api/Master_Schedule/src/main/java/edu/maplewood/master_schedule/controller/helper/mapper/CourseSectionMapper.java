package edu.maplewood.master_schedule.controller.helper.mapper;

import edu.maplewood.master_schedule.controller.dto.response.CourseResponse;
import edu.maplewood.master_schedule.controller.dto.response.CourseSectionResponse;
import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.dto.response.ScheduleAssignmentResponse;
import edu.maplewood.master_schedule.controller.dto.response.SemesterResponse;
import edu.maplewood.master_schedule.controller.dto.response.StudentResponse;
import edu.maplewood.master_schedule.controller.dto.response.TeacherResponse;
import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.CourseSection;
import edu.maplewood.master_schedule.entity.ScheduleAssignment;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.entity.Student;
import edu.maplewood.master_schedule.entity.Teacher;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

public class CourseSectionMapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(CourseSectionMapper.class);

  public static CourseSectionResponse toResponse(CourseSection courseSection) {
    LOGGER.debug("Converting courseSection to DTO");

    return new CourseSectionResponse(
        courseSection.getId(),
        courseSection.getCode(),
        courseSection.getCreatedAt(),
        courseResponse(courseSection.getCourse()),
        semesterResponse(courseSection.getSemester()),
        teacherResponse(courseSection.getTeacher()),
        scheduleResponse(courseSection.getScheduleAssignments()),
        studentsResponse(courseSection.getStudents())
    );
  }

  private static CourseResponse courseResponse(Course course) {
    return CourseMapper.toResponse(course);
  }

  private static SemesterResponse semesterResponse(Semester semester) {
    return SemesterMapper.toResponse(semester);
  }

  private static TeacherResponse teacherResponse(Teacher teacher) {
    return TeacherMapper.toResponse(teacher);
  }

  private static List<StudentResponse> studentsResponse(List<Student> students) {
    return StudentMapper.toResponse(students);
  }

  private static List<ScheduleAssignmentResponse> scheduleResponse(
      List<ScheduleAssignment> scheduleAssignments) {
    return ScheduleAssignmentMapper.toResponse(scheduleAssignments);
  }

  public static List<CourseSectionResponse> toResponse(List<CourseSection> courses) {
    LOGGER.debug("Converting Course Sections List to DTO");
    return courses.stream().map(CourseSectionMapper::toResponse).toList();
  }

  public static ResponseList<CourseSectionResponse> toResponse(Page<CourseSection> sections) {
    LOGGER.debug(
        "Mapping Page of Course Sections entities to ResponseList<CourseSectionResponse>. Total sections: {}",
        sections.getTotalElements());
    List<CourseSection> sectionList = sections.getContent();
    List<CourseSectionResponse> sectionResponses = toResponse(sectionList);

    return new ResponseList<>(sections.getTotalElements(),
        sections.getNumber(), sections.getSize(), sectionResponses);
  }
}
