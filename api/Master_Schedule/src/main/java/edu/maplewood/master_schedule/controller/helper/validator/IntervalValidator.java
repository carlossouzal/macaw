package edu.maplewood.master_schedule.controller.helper.validator;

import edu.maplewood.master_schedule.controller.helper.annotation.ValidInterval;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class IntervalValidator implements ConstraintValidator<ValidInterval, Object> {

  private String minFieldName;
  private String maxFieldName;

  @Override
  public void initialize(ValidInterval constraintAnnotation) {
    this.minFieldName = constraintAnnotation.minField();
    this.maxFieldName = constraintAnnotation.maxField();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    try {
      Field minField = value.getClass().getDeclaredField(minFieldName);
      Field maxField = value.getClass().getDeclaredField(maxFieldName);
      minField.setAccessible(true);
      maxField.setAccessible(true);

      Object min = minField.get(value);
      Object max = maxField.get(value);

      if (min == null || max == null) {
        return true;
      }

      if (min instanceof Comparable<?> && max instanceof Comparable<?> m2) {
        return ((Comparable<Object>) min).compareTo(m2) <= 0;
      }

      return true;
    } catch (IllegalAccessException | NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
}
