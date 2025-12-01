package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.entity.Classroom;
import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.CourseSection;
import edu.maplewood.master_schedule.entity.RoomType;
import edu.maplewood.master_schedule.entity.ScheduleAssignment;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.entity.Specialization;
import edu.maplewood.master_schedule.entity.Teacher;
import edu.maplewood.master_schedule.exception.NoAvailableResource;
import edu.maplewood.master_schedule.repository.ScheduleAssignmentRepository;
import jakarta.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

  private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
  private final ScheduleAssignmentRepository scheduleAssignmentRepository;
  private final CourseService courseService;
  private final CourseSectionService courseSectionService;
  private final StudentService studentService;
  private final TeacherService teacherService;
  private final ClassroomService classroomService;

  @Autowired
  public ScheduleService(
      StudentService studentService,
      CourseService courseService,
      CourseSectionService courseSectionService,
      TeacherService teacherService,
      ClassroomService classroomService,
      ScheduleAssignmentRepository scheduleAssignmentRepository) {
    this.courseService = courseService;
    this.courseSectionService = courseSectionService;
    this.teacherService = teacherService;
    this.classroomService = classroomService;
    this.studentService = studentService;
    this.scheduleAssignmentRepository = scheduleAssignmentRepository;
  }

  @Transactional
  public List<CourseSection> generateSchedule(Semester semester) {
    logger.info("Generating schedule for semester: {}", semester.getName());

    return getCourseSection(semester).stream()
        .map(this::scheduleClassroom)
        .toList();
  }

  private List<CourseSection> getCourseSection(Semester semester) {
    List<CourseSection> sections = courseSectionService.sectionBySemester(semester);
    if (!sections.isEmpty()) {
      return sections;
    }

    return generateCourseSections(semester);
  }

  private List<CourseSection> generateCourseSections(Semester semester) {
    List<Course> courses = courseService.findBySemesterOrder(semester.getOrderInYear());
    return courses.stream()
        .filter(this::courseHasDemand)
        .map(course -> courseSectionService.createSection(course, semester))
        .toList();
  }

  private boolean courseHasDemand(Course course) {
    return studentService.getNumberOfStudentsThatCanEnroll(course) > 0;
  }

  private CourseSection scheduleClassroom(CourseSection courseSection) {
    logger.debug("Assigning classroom to course section: {}", courseSection.getCode());
    if (isFullySchedule(courseSection)) {
      return courseSection;
    }

    List<Teacher> teachers = availableTeachers(courseSection);
    List<Classroom> classroom = availableClassrooms(courseSection);

    return schedule(courseSection, teachers, classroom);
  }

  private boolean isFullySchedule(CourseSection section) {
    long totalDuration = section.getScheduleAssignments().size();
    long requiredDuration = section.getCourse().getHoursPerWeek();

    return totalDuration >= requiredDuration;
  }

  private List<Teacher> availableTeachers(CourseSection courseSection) {
    Specialization specialization = courseSection.getCourse().getSpecialization();
    return teacherService.nextsAvailableFor(specialization);
  }

  private List<Classroom> availableClassrooms(CourseSection courseSection) {
    RoomType roomType = courseSection.getCourse().getSpecialization().getRoomType();
    return classroomService.nextAvailableFor(roomType);
  }

  private CourseSection schedule(CourseSection section, List<Teacher> teachers,
      List<Classroom> classrooms) {
    if (teachers.isEmpty() || classrooms.isEmpty()) {
      return section;
    }

    int needed = section.getCourse().getHoursPerWeek();
    int already = section.getScheduleAssignments().size();

    List<DayOfWeek> teachingDays = Arrays.stream(DayOfWeek.values())
        .filter(day -> day != DayOfWeek.SUNDAY && day != DayOfWeek.SATURDAY).toList();

    for (Teacher teacher : teachers) {
      section.setTeacher(teacher);

      for (Classroom classroom : classrooms) {
        int remaining = needed - already;
        List<ScheduleAssignment> staged = new ArrayList<>();
        Map<DayOfWeek, List<Integer>> daySlots = new HashMap<>();

        for (DayOfWeek day : teachingDays) {
          if (countTeacherHours(teacher, day) >= teacher.getMaxDailyHours()) {
            continue;
          }
          if (remaining <= 0) {
            break;
          }

          for (int slot = 1; slot <= 7 && remaining > 0; slot++) {
            if (isTeacherBusy(teacher, day, slot) || isClassroomBusy(classroom, day, slot)) {
              continue;
            }

            List<Integer> used = daySlots.computeIfAbsent(day, d -> new ArrayList<>());
            if (!validSlotSequence(used, slot)) {
              continue;
            }

            ScheduleAssignment assignment = createAssignment(section, classroom, day, slot);
            staged.add(assignment);
            used.add(slot);
            remaining--;
          }
        }

        if (remaining == 0) {
          return assign(staged, section);
        }
      }
    }

    int residual = section.getCourse().getHoursPerWeek() - section.getScheduleAssignments().size();
    if (residual > 0) {
      logger.warn("Could not fully schedule course section {}. Remaining hours: {}",
          section.getCode(), residual);
      throw new NoAvailableResource(
          "Insufficient resources to schedule course section: " + section.getCode());
    }

    return section;
  }

  private ScheduleAssignment createAssignment(CourseSection section, Classroom classroom,
      DayOfWeek day, int slot) {
    ScheduleAssignment assignment = new ScheduleAssignment();
    assignment.setCourseSection(section);
    assignment.setClassroom(classroom);
    assignment.setDayOfWeek(day);
    assignment.setTimeSlot(slot);
    return scheduleAssignmentRepository.save(assignment);
  }

  private CourseSection assign(List<ScheduleAssignment> assignments, CourseSection section) {
    section.setScheduleAssignments(assignments);
    return courseSectionService.updateSection(section);
  }

  private boolean validSlotSequence(List<Integer> slots, int newSlot) {
    List<Integer> test = new ArrayList<>(slots);
    test.add(newSlot);

    if (test.size() >= 3) {
      return false;
    }

    return true;
  }

  private int countTeacherHours(Teacher teacher, DayOfWeek day) {
    return teacher.getCourseSections().stream().map(CourseSection::getScheduleAssignments)
        .flatMap(List::stream)
        .filter(a -> a.getDayOfWeek() == day)
        .mapToInt(a -> 1)
        .sum();
  }

  private boolean isTeacherBusy(Teacher teacher, DayOfWeek day, int slot) {
    return teacher.getCourseSections().stream().map(CourseSection::getScheduleAssignments)
        .flatMap(List::stream)
        .anyMatch(a -> a.getDayOfWeek() == day && a.getTimeSlot() == slot);
  }

  private boolean isClassroomBusy(Classroom room, DayOfWeek day, int slot) {
    return room.getAssignments().stream()
        .anyMatch(a -> a.getDayOfWeek() == day && a.getTimeSlot() == slot);
  }
}
