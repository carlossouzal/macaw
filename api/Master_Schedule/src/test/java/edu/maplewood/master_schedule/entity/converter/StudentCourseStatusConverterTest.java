package edu.maplewood.master_schedule.entity.converter;


import edu.maplewood.master_schedule.entity.StudentCourseHistory.StudentCourseStatus;

public class StudentCourseStatusConverterTest extends
    AbstractLowercaseEnumConverterTest<StudentCourseStatus> {

  private final StudentCourseStatusConverter converter = new StudentCourseStatusConverter();

  @Override
  protected LowercaseEnumConverter<StudentCourseStatus> converter() {
    return converter;
  }

  @Override
  protected Class<StudentCourseStatus> enumClass() {
    return StudentCourseStatus.class;
  }
}
