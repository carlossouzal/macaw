package edu.maplewood.master_schedule.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, String> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

  @Override
  public String convertToDatabaseColumn(LocalDate attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.format(FORMATTER);
  }

  @Override
  public LocalDate convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    return LocalDate.parse(dbData, FORMATTER);
  }
}
