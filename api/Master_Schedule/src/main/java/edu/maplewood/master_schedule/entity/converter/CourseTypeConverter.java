package edu.maplewood.master_schedule.entity.converter;

import edu.maplewood.master_schedule.entity.Course.CourseType;

public class CourseTypeConverter extends LowercaseEnumConverter<CourseType> {

  public CourseTypeConverter() {
    super(CourseType.class);
  }
}
