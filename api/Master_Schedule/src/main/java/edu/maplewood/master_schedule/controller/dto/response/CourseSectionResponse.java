package edu.maplewood.master_schedule.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public record CourseSectionResponse(
    Long id,
    String code,
    @JsonProperty("created_at") LocalDateTime createdAt,
    CourseResponse course,
    SemesterResponse semester,
    TeacherResponse teacher,
    @JsonProperty("schedule_assignments") List<ScheduleAssignmentResponse> scheduleAssignments,
    List<StudentResponse> students
) {

}
