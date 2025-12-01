package edu.maplewood.master_schedule.entity.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;

class LocalDateAttributeConverterTest {

  private final LocalDateAttributeConverter converter = new LocalDateAttributeConverter();

  @Test
  void convertToDatabaseColumnNull() {
    assertNull(converter.convertToDatabaseColumn(null));
  }

  @Test
  void convertToDatabaseColumnValid() {
    LocalDate date = LocalDate.of(2024, 3, 9);
    String result = converter.convertToDatabaseColumn(date);
    assertEquals("2024-03-09", result);
  }

  @Test
  void convertToEntityAttributeNull() {
    assertNull(converter.convertToEntityAttribute(null));
  }

  @Test
  void convertToEntityAttributeValid() {
    LocalDate result = converter.convertToEntityAttribute("1999-12-31");
    assertEquals(LocalDate.of(1999, 12, 31), result);
  }

  @Test
  void roundTrip() {
    LocalDate original = LocalDate.of(2030, 6, 15);
    String db = converter.convertToDatabaseColumn(original);
    LocalDate back = converter.convertToEntityAttribute(db);
    assertEquals(original, back);
  }

  @Test
  void convertToEntityAttributeInvalidFormat() {
    assertThrows(DateTimeParseException.class,
        () -> converter.convertToEntityAttribute("15-06-2030"));
  }

  @Test
  void leadingZeros() {
    LocalDate date = LocalDate.of(2000, 1, 2);
    assertEquals("2000-01-02", converter.convertToDatabaseColumn(date));
  }
}
