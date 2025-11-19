package edu.maplewood.master_schedule.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.maplewood.master_schedule.entity.Semester.OrderInYear;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record SemesterResponse(
    long id,
    String name,
    int year,
    @JsonProperty("order_in_year") OrderInYear orderInYear,
    @JsonProperty("start_date") LocalDate startDate,
    @JsonProperty("end_date") LocalDate endDate,
    @JsonProperty("is_active") boolean isActive,
    @JsonProperty("created_at") LocalDateTime createdAt
) {

}