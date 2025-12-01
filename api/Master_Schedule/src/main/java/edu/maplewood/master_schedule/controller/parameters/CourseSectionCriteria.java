package edu.maplewood.master_schedule.controller.parameters;

import edu.maplewood.master_schedule.config.Constants;
import edu.maplewood.master_schedule.controller.helper.annotation.ValidSortBy;
import edu.maplewood.master_schedule.entity.CourseSection;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record CourseSectionCriteria(
    @Min(0)
    Integer page,
    @Min(1)
    Integer size,
    @ValidSortBy(entity = CourseSection.class, message = "Invalid sortBy parameter for Course Section")
    String sortBy,
    @Pattern(regexp = "ASC|DESC", message = "Sort direction must be ASC or DESC")
    String sortDirection,
    String code,
    Long courseId,
    Long semesterId,
    Long teacherId
) {

  public CourseSectionCriteria {
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
