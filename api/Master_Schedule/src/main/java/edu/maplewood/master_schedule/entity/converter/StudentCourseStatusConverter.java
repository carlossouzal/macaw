package edu.maplewood.master_schedule.entity.converter;

import edu.maplewood.master_schedule.entity.StudentCourseHistory.StudentCourseStatus;
import jakarta.persistence.Converter;

@Converter
public class StudentCourseStatusConverter extends LowercaseEnumConverter<StudentCourseStatus> {

  public StudentCourseStatusConverter() {
    super(StudentCourseStatus.class);
  }

}
