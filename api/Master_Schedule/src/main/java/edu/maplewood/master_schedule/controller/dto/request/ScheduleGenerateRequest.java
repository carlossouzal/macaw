package edu.maplewood.master_schedule.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record ScheduleGenerateRequest(
    @JsonProperty("semester_id")
    @NotNull
    Long semesterId
) {

}
