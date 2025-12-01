package edu.maplewood.master_schedule.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record TeacherResponse(
    Long id,
    @JsonProperty("first_name") String firstName,
    @JsonProperty("last_name") String lastName,
    String email,
    @JsonProperty("max_daily_hours") Integer maxDailyHours,
    @JsonProperty("created_at") LocalDateTime createdAt,
    SpecializationResponse specialization
) {

}
