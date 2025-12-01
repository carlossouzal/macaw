package edu.maplewood.master_schedule.controller.helper.annotation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ValidIntervalTest {

  private static Validator validator;

  @ValidInterval(minField = "createdAtBegin", maxField = "createdAtEnd")
  record ValidIntervalDto(
      LocalDateTime createdAtBegin,
      LocalDateTime createdAtEnd
  ) {

  }

  @BeforeAll
  static void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void validIntervalOnlyMin() {
    Set<ConstraintViolation<ValidIntervalDto>> violations = validator.validate(
        new ValidIntervalDto(LocalDateTime.now().minusDays(1), null));
    assertTrue(violations.isEmpty());
  }

  @Test
  void validIntervalOnlyMax() {
    Set<ConstraintViolation<ValidIntervalDto>> violations = validator.validate(
        new ValidIntervalDto(null, LocalDateTime.now()));
    assertTrue(violations.isEmpty());
  }

  @Test
  void validIntervalMinAndMax() {
    Set<ConstraintViolation<ValidIntervalDto>> violations = validator.validate(
        new ValidIntervalDto(LocalDateTime.now().minusDays(1), LocalDateTime.now()));
    assertTrue(violations.isEmpty());
  }

  @Test
  void invalidIntervalMinAndMax() {
    Set<ConstraintViolation<ValidIntervalDto>> violations = validator.validate(
        new ValidIntervalDto(LocalDateTime.now(), LocalDateTime.now().minusDays(1)));
    assertFalse(violations.isEmpty());
  }
}
