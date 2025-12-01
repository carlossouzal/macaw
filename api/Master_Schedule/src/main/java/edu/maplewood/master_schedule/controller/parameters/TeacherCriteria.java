package edu.maplewood.master_schedule.controller.parameters;

import edu.maplewood.master_schedule.config.Constants;
import edu.maplewood.master_schedule.controller.helper.annotation.ValidInterval;
import edu.maplewood.master_schedule.controller.helper.annotation.ValidSortBy;
import edu.maplewood.master_schedule.entity.Teacher;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

@ValidInterval(minField = "maxDailyHoursBegin", maxField = "maxDailyHoursEnd")
@ValidInterval(minField = "createdAtBegin", maxField = "createdAtEnd")
public record TeacherCriteria(
    @Min(0)
    Integer page,
    @Min(1)
    Integer size,
    @ValidSortBy(entity = Teacher.class, message = "Invalid sortBy parameter for Teacher")
    String sortBy,
    @Pattern(regexp = "ASC|DESC", message = "Sort direction must be ASC or DESC")
    String sortDirection,
    String name,
    String email,
    @Min(0)
    Integer maxDailyHoursBegin,
    @Min(0)
    Integer maxDailyHoursEnd,
    @Min(0)
    Long specializationId,
    LocalDateTime createdAtBegin,
    LocalDateTime createdAtEnd
) {

  public TeacherCriteria {
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
