package edu.maplewood.master_schedule.entity.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.maplewood.master_schedule.entity.Semester.OrderInYear;
import org.junit.jupiter.api.Test;

class SemesterOrderConverterTest {

  private final SemesterOrderConverter converter = new SemesterOrderConverter();

  @Test
  void convertToDatabaseColumnAllEnumValues() {
    for (OrderInYear e : OrderInYear.values()) {
      Integer dbValue = converter.convertToDatabaseColumn(e);
      assertEquals(e.getOrder(), dbValue);
    }
  }

  @Test
  void convertToEntityAttributeAllValidIntegers() {
    for (OrderInYear e : OrderInYear.values()) {
      OrderInYear restored = converter.convertToEntityAttribute(e.getOrder());
      assertEquals(e, restored);
    }
  }

  @Test
  void nullHandling() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertNull(converter.convertToEntityAttribute(null));
  }

  @Test
  void invalidValueThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> converter.convertToEntityAttribute(-1));
  }
}
