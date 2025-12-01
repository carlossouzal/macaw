package edu.maplewood.master_schedule.controller.parameters;

import edu.maplewood.master_schedule.config.Constants;
import edu.maplewood.master_schedule.controller.helper.annotation.ValidInterval;
import edu.maplewood.master_schedule.controller.helper.annotation.ValidSortBy;
import edu.maplewood.master_schedule.entity.Classroom;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;


@ValidInterval(minField = "capacityMin", maxField = "capacityMax")
public record ClassroomCriteria(
    @Min(0)
    Integer page,
    @Min(1)
    Integer size,
    @ValidSortBy(entity = Classroom.class, message = "Invalid sortBy parameter for Classroom")
    String sortBy,
    @Pattern(regexp = "ASC|DESC", message = "Sort direction must be ASC or DESC")
    String sortDirection,
    String name,
    @Min(0)
    Integer capacityMin,
    @Min(0)
    Integer capacityMax,
    String equipment,
    @Min(0)
    Integer floor,
    Long roomTypeId,
    Boolean available
) {

  public ClassroomCriteria {
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
