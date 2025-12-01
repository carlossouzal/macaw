package edu.maplewood.master_schedule.entity.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public abstract class AbstractLowercaseEnumConverterTest<E extends Enum<E>> {

  protected abstract LowercaseEnumConverter<E> converter();

  protected abstract Class<E> enumClass();

  @Test
  void convertsAllEnumValuesToLowercaseStrings() {
    for (E e : enumClass().getEnumConstants()) {
      String dbValue = converter().convertToDatabaseColumn(e);
      assertEquals(e.name().toLowerCase(), dbValue);
    }
  }

  @Test
  void convertsAllLowercaseStringsToEnumValues() {
    for (E e : enumClass().getEnumConstants()) {
      assertEquals(e, converter().convertToEntityAttribute(e.name().toLowerCase()));
    }
  }

  @Test
  void nullHandling() {
    assertNull(converter().convertToDatabaseColumn(null));
    assertNull(converter().convertToEntityAttribute(null));
  }

  @Test
  void throwsExceptionForInvalidDatabaseValue() {
    assertThrows(IllegalArgumentException.class,
        () -> converter().convertToEntityAttribute("___invalid___"));
  }
}
