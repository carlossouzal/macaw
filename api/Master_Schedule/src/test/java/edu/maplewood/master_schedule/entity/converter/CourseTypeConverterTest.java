package edu.maplewood.master_schedule.entity.converter;

import edu.maplewood.master_schedule.entity.Course.CourseType;

public class CourseTypeConverterTest extends AbstractLowercaseEnumConverterTest<CourseType> {

  private final CourseTypeConverter converter = new CourseTypeConverter();

  @Override
  protected LowercaseEnumConverter<CourseType> converter() {
    return converter;
  }

  @Override
  protected Class<CourseType> enumClass() {
    return CourseType.class;
  }
}
