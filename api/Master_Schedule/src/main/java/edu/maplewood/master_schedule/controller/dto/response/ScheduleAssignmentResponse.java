package edu.maplewood.master_schedule.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.DayOfWeek;

public record ScheduleAssignmentResponse(
    Long id,
    ClassroomResponse classroom,
    @JsonProperty("day_of_week") DayOfWeek dayOfWeek,
    @JsonProperty("time_slot") Integer timeSlot,
    @JsonProperty("is_active") Boolean isActive
) {

}
