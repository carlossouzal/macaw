package edu.maplewood.master_schedule.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.maplewood.master_schedule.controller.helper.annotation.CurrentOrFutureYear;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Optional;

public record CreateSemesterRequest(
    @Size(max = 50, message = "Name must not exceed 50 characters")
    @NotBlank(message = "Name must not be blank")
    String name,
    @CurrentOrFutureYear
    int year,
    @JsonProperty("order_in_year")
    @Pattern(regexp = "FALL|SPRING", message = "Order in year must be either 'FALL' or 'SPRING'")
    String orderInYear,
    @JsonProperty("start_date")
    Optional<LocalDate> startDate,
    @JsonProperty("end_date")
    Optional<LocalDate> endDate
) {

}