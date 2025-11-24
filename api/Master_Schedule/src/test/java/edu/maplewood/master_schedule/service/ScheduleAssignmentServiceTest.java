package edu.maplewood.master_schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.maplewood.master_schedule.entity.Classroom;
import edu.maplewood.master_schedule.entity.CourseSection;
import edu.maplewood.master_schedule.entity.ScheduleAssignment;
import edu.maplewood.master_schedule.repository.ScheduleAssignmentRepository;
import java.time.DayOfWeek;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScheduleAssignmentServiceTest {

  private ScheduleAssignmentRepository repository;
  private ScheduleAssignmentService service;

  @BeforeEach
  public void setUp() {
    repository = mock(ScheduleAssignmentRepository.class);
    service = new ScheduleAssignmentService(repository);
  }

  @Test
  public void testFindAll() {
    ScheduleAssignment assignment = createScheduleAssignment();
    when(repository.findAll()).thenReturn(List.of(assignment));

    List<ScheduleAssignment> assignments = service.findAll();

    verify(repository).findAll();

    assertThat(assignments).containsExactly(assignment);
  }

  public static ScheduleAssignment createScheduleAssignment() {
    CourseSection courseSection = mock(CourseSection.class);
    Classroom classroom = mock(Classroom.class);
    ScheduleAssignment assignment = new ScheduleAssignment();
    assignment.setId(1L);
    assignment.setCourseSection(courseSection);
    assignment.setClassroom(classroom);
    assignment.setDayOfWeek(DayOfWeek.MONDAY);
    assignment.setTimeSlot(1);
    assignment.setIsActive(true);
    return assignment;
  }
}
