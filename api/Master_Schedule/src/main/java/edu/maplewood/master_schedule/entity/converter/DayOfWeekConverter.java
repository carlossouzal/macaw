package edu.maplewood.master_schedule.entity.converter;

import jakarta.persistence.Converter;
import java.time.DayOfWeek;

@Converter
public class DayOfWeekConverter extends LowercaseEnumConverter<DayOfWeek> {

  public DayOfWeekConverter() {
    super(DayOfWeek.class);
  }


}
