package edu.maplewood.master_schedule.entity.converter;

import java.time.DayOfWeek;

public class DayOfWeekConverterTest extends AbstractLowercaseEnumConverterTest<DayOfWeek> {

  private final DayOfWeekConverter converter = new DayOfWeekConverter();

  @Override
  protected LowercaseEnumConverter<DayOfWeek> converter() {
    return converter;
  }

  @Override
  protected Class<DayOfWeek> enumClass() {
    return DayOfWeek.class;
  }
}
