package edu.maplewood.master_schedule.controller.helper.mapper;

import edu.maplewood.master_schedule.controller.dto.response.ClassroomResponse;
import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.dto.response.ScheduleAssignmentResponse;
import edu.maplewood.master_schedule.entity.ScheduleAssignment;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

public class ScheduleAssignmentMapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleAssignmentMapper.class);

  public static ScheduleAssignmentResponse toResponse(ScheduleAssignment assignment) {
    LOGGER.debug("Converting ScheduleAssignment to DTO");

    ClassroomResponse classroom = null;
    if (assignment.getClassroom() != null) {
      classroom = ClassroomMapper.toResponse(assignment.getClassroom());
    }

    return new ScheduleAssignmentResponse(
        assignment.getId(),
        classroom,
        assignment.getDayOfWeek(),
        assignment.getTimeSlot(),
        assignment.getIsActive()
    );
  }

  public static List<ScheduleAssignmentResponse> toResponse(List<ScheduleAssignment> assignments) {
    LOGGER.debug("Converting ScheduleAssignment List to DTO");
    return assignments.stream().map(ScheduleAssignmentMapper::toResponse).toList();
  }

  public static ResponseList<ScheduleAssignmentResponse> toResponse(
      Page<ScheduleAssignment> assignments) {
    LOGGER.debug(
        "Mapping Page of ScheduleAssignment entities to ResponseList<ScheduleAssignmentResponse>. Total assignments: {}",
        assignments.getTotalElements());
    List<ScheduleAssignment> assignmentList = assignments.getContent();
    List<ScheduleAssignmentResponse> assignmentResponses = toResponse(assignmentList);

    return new ResponseList<>(assignments.getTotalElements(),
        assignments.getNumber(), assignments.getSize(), assignmentResponses);
  }

}
