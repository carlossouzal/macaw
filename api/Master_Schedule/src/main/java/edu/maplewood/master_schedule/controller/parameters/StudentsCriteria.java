package edu.maplewood.master_schedule.controller.parameters;

import edu.maplewood.master_schedule.config.Constants;
import edu.maplewood.master_schedule.controller.helper.annotation.ValidInterval;
import edu.maplewood.master_schedule.controller.helper.annotation.ValidSortBy;
import edu.maplewood.master_schedule.entity.Student;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@ValidInterval(minField = "gradeLevelMin", maxField = "gradeLevelMax")
@ValidInterval(minField = "enrollmentYearMin", maxField = "enrollmentYearMax")
@ValidInterval(minField = "expectedGraduationYearMin", maxField = "expectedGraduationYearMax")
public record StudentsCriteria(
    @Min(0)
    Integer page,
    @Min(1)
    Integer size,
    @ValidSortBy(entity = Student.class, message = "Invalid sortBy parameter for Semester")
    String sortBy,
    @Pattern(regexp = "ASC|DESC", message = "Sort direction must be ASC or DESC")
    String sortDirection,
    String name,
    String email,
    @Min(9)
    Integer gradeLevelMin,
    @Max(12)
    Integer gradeLevelMax,
    Integer enrollmentYearMin,
    Integer enrollmentYearMax,
    Integer expectedGraduationYearMin,
    Integer expectedGraduationYearMax,
    Long prerequiredCourseId,
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "Status must be an existing status (ACTIVE)")
    String status
) {

  public StudentsCriteria {
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
