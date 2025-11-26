package edu.maplewood.master_schedule.controller.helper.annotation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CurrentOrFutureYearTest {

  private static Validator validator;


  record CurrentOrFutureYearDTO(
      @CurrentOrFutureYear
      int year
  ) {

  }

  @BeforeAll
  static void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void validCurrentOrFutureYear() {
    Set<ConstraintViolation<CurrentOrFutureYearDTO>> violations = validator.validate(
        new CurrentOrFutureYearDTO(2067));
    assertTrue(violations.isEmpty());
  }

  @Test
  void invalidCurrentOrFutureYear() {
    Set<ConstraintViolation<CurrentOrFutureYearDTO>> violations = validator.validate(
        new CurrentOrFutureYearDTO(2000));
    assertFalse(violations.isEmpty());
  }

}
