package edu.maplewood.master_schedule.controller.helper.validator;

import edu.maplewood.master_schedule.controller.helper.annotation.CurrentOrFutureYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class YearValidator implements ConstraintValidator<CurrentOrFutureYear, Integer> {

  @Override
  public void initialize(CurrentOrFutureYear constraintAnnotation) {
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }
    int currentYear = Year.now().getValue();
    return value >= currentYear;
  }
}
