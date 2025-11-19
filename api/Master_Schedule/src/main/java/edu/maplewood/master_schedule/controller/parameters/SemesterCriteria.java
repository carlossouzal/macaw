package edu.maplewood.master_schedule.controller.parameters;

import edu.maplewood.master_schedule.config.Constants;
import edu.maplewood.master_schedule.controller.helper.annotation.ValidInterval;
import edu.maplewood.master_schedule.controller.helper.annotation.ValidSortBy;
import edu.maplewood.master_schedule.entity.Semester;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ValidInterval(minField = "yearMin", maxField = "yearMax")
@ValidInterval(minField = "startDateBegin", maxField = "startDateEnd")
@ValidInterval(minField = "endDateBegin", maxField = "endDateEnd")
@ValidInterval(minField = "createdAtBegin", maxField = "createdAtEnd")
public record SemesterCriteria(
    @Min(0)
    Integer page,
    @Min(1)
    Integer size,
    @ValidSortBy(entity = Semester.class, message = "Invalid sortBy parameter for Semester")
    String sortBy,
    @Pattern(regexp = "ASC|DESC", message = "Sort direction must be ASC or DESC")
    String sortDirection,
    String name,
    @Min(value = 1900, message = "Year must be a valid year")
    Integer yearMin,
    @Min(value = 1900, message = "Year must be a valid year")
    Integer yearMax,
    @Pattern(regexp = "FALL|SPRING", message = "Order in year must be either 'FALL' or 'SPRING'")
    String orderInYear,
    LocalDate startDateBegin,
    LocalDate startDateEnd,
    LocalDate endDateBegin,
    LocalDate endDateEnd,
    Boolean isActive,
    LocalDateTime createdAtBegin,
    LocalDateTime createdAtEnd
) {

  public SemesterCriteria {
    if (page == null) {
      page = Constants.DEFAULT_PAGE_NUMBER;
    }
    if (size == null) {
      size = Constants.DEFAULT_PAGE_SIZE;
    }
    if (sortBy == null || sortBy.isBlank()) {
      sortBy = Constants.DEFAULT_SORT_BY;
    }
    if (sortDirection == null || sortDirection.isBlank()) {
      sortDirection = Constants.DEFAULT_SORT_DIRECTION;
    }
  }

}
