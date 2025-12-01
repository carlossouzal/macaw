package edu.maplewood.master_schedule.entity.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LowercaseEnumConverterTest {

  private final LowercaseEnumConverter<TestEnum> converter =
      new LowercaseEnumConverter<>(TestEnum.class);

  @Test
  void convertToDatabaseColumnLowercasesEnumName() {
    assertEquals("first_value", converter.convertToDatabaseColumn(TestEnum.FIRST_VALUE));
    assertEquals("second_value", converter.convertToDatabaseColumn(TestEnum.SECOND_VALUE));
    assertEquals("third_value", converter.convertToDatabaseColumn(TestEnum.THIRD_VALUE));
  }

  @Test
  void convertToDatabaseColumnNullReturnsNull() {
    assertNull(converter.convertToDatabaseColumn(null));
  }

  @Test
  void convertToEntityAttributeLowercasesEnumName() {
    assertEquals(TestEnum.FIRST_VALUE, converter.convertToEntityAttribute("first_value"));
    assertEquals(TestEnum.SECOND_VALUE, converter.convertToEntityAttribute("second_value"));
    assertEquals(TestEnum.THIRD_VALUE, converter.convertToEntityAttribute("third_value"));
  }

  @Test
  void convertToEntityAttributeNullReturnsNull() {
    assertNull(converter.convertToEntityAttribute(null));
  }

  @Test
  void convertToEntityAttributeInvalidValueThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      converter.convertToEntityAttribute("invalid_value");
    });
  }


  private enum TestEnum {
    FIRST_VALUE,
    SECOND_VALUE,
    THIRD_VALUE
  }
}
