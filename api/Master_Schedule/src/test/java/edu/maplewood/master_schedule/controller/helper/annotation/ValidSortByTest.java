package edu.maplewood.master_schedule.controller.helper.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.Instant;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ValidSortByTest {

  private static Validator validator;

  static class SortBytDto {

    @ValidSortBy(entity = DummyEntity.class, allowed = {"name", "createdAt"})
    private String sortBy;

    SortBytDto(String sortBy) {
      this.sortBy = sortBy;
    }
  }

  static class DummyEntity {

    private String name;
    private Instant createdAt;
  }

  @BeforeAll
  static void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void validAllowedFieldPassesValidation() {
    Set<ConstraintViolation<SortBytDto>> violations = validator.validate(new SortBytDto("name"));
    assertTrue(violations.isEmpty());
  }

  @Test
  void invalidFieldFailsValidation() {
    Set<ConstraintViolation<SortBytDto>> violations = validator.validate(
        new SortBytDto("notAField"));
    assertFalse(violations.isEmpty());
    assertEquals("Invalid sortBy parameter", violations.iterator().next().getMessage());
  }

  @Test
  void nullValuePassesWhenAllowed() {
    Set<ConstraintViolation<SortBytDto>> violations = validator.validate(new SortBytDto(null));
    assertTrue(violations.isEmpty());
  }
}
