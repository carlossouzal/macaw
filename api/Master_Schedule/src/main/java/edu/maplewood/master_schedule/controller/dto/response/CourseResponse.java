package edu.maplewood.master_schedule.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.maplewood.master_schedule.entity.Course.CourseType;
import edu.maplewood.master_schedule.entity.Semester;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CourseResponse(
    Long id,
    String code,
    String name,
    String description,
    BigDecimal credits,
    @JsonProperty("hours_per_week") Integer hoursPerWeek,
    @JsonProperty("course_type") CourseType courseType,
    @JsonProperty("grade_level_min") Integer gradeLevelMin,
    @JsonProperty("grade_level_max") Integer gradeLevelMax,
    @JsonProperty("semester_order") Semester.OrderInYear semesterOrder,
    @JsonProperty("created_at") LocalDateTime createdAt,
    SpecializationResponse specialization,
    CourseResponse prerequisite
) {

}
