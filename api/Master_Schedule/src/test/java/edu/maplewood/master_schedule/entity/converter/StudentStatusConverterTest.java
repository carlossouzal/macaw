package edu.maplewood.master_schedule.entity.converter;

import edu.maplewood.master_schedule.entity.Student.StudentStatus;

public class StudentStatusConverterTest extends
    AbstractLowercaseEnumConverterTest<StudentStatus> {

  private final StudentStatusConverter converter = new StudentStatusConverter();

  @Override
  protected LowercaseEnumConverter<StudentStatus> converter() {
    return converter;
  }

  @Override
  protected Class<StudentStatus> enumClass() {
    return StudentStatus.class;
  }
}
