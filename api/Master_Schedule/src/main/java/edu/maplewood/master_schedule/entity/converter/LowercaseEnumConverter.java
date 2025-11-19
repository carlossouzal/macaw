package edu.maplewood.master_schedule.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class LowercaseEnumConverter<T extends Enum<T>> implements AttributeConverter<T, String> {

  private final Class<T> enumType;

  public LowercaseEnumConverter(Class<T> enumType) {
    this.enumType = enumType;
  }

  @Override
  public String convertToDatabaseColumn(T attribute) {
    if (attribute == null) {
      return null;
    }

    return attribute.name().toLowerCase();
  }

  @Override
  public T convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }

    return Enum.valueOf(enumType, dbData.toUpperCase());
  }

}
