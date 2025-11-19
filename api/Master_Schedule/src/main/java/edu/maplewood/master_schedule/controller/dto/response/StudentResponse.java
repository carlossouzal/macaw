package edu.maplewood.master_schedule.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.maplewood.master_schedule.entity.Student;
import java.time.LocalDateTime;

public record StudentResponse(
    Long id,
    @JsonProperty("first_name") String firstName,
    @JsonProperty("last_name") String lastName,
    String email,
    @JsonProperty("grade_level") Integer gradeLevel,
    @JsonProperty("enrollment_year") Integer enrollmentYear,
    @JsonProperty("expected_graduation_year") Integer expectedGraduationYear,
    Student.StudentStatus status,
    @JsonProperty("created_at") LocalDateTime createdAt,
    Double gpa
) {

}
