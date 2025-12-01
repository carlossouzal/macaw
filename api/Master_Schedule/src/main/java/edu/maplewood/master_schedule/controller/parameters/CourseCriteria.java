package edu.maplewood.master_schedule.controller.parameters;

import edu.maplewood.master_schedule.config.Constants;
import edu.maplewood.master_schedule.controller.helper.annotation.ValidInterval;
import edu.maplewood.master_schedule.controller.helper.annotation.ValidSortBy;
import edu.maplewood.master_schedule.entity.Course;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

@ValidInterval(minField = "creditsMin", maxField = "creditsMax")
@ValidInterval(minField = "hoursPerWeekMin", maxField = "hoursPerWeekMax")
@ValidInterval(minField = "gradeLevelMin", maxField = "gradeLevelMax")
public record CourseCriteria(
    @Min(0)
    Integer page,
    @Min(1)
    Integer size,
    @ValidSortBy(entity = Course.class, message = "Invalid sortBy parameter for Course")
    String sortBy,
    @Pattern(regexp = "ASC|DESC", message = "Sort direction must be ASC or DESC")
    String sortDirection,
    String code,
    String name,
    String description,
    BigDecimal creditsMin,
    BigDecimal creditsMax,
    Integer hoursPerWeekMin,
    Integer hoursPerWeekMax,
    @Pattern(regexp = "CORE|ELECTIVE", message = "course type must be either 'CORE' or 'ELECTIVE'")
    String courseType,
    Integer gradeLevelMin,
    Integer gradeLevelMax,
    @Pattern(regexp = "FALL|SPRING", message = "Order in year must be either 'FALL' or 'SPRING'")
    String semesterOrder,
    Long specializationId,
    Long prerequisiteId
) {

  public CourseCriteria {
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
