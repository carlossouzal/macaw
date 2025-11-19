package edu.maplewood.master_schedule.controller.helper.validator;

import edu.maplewood.master_schedule.controller.helper.annotation.ValidSortBy;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SortByValidator implements ConstraintValidator<ValidSortBy, String> {

  private Set<String> validFields = Set.of();

  @Override
  public void initialize(ValidSortBy constraintAnnotation) {
    Class<?> entityClass = constraintAnnotation.entity();
    validFields = new HashSet<>(Arrays.asList(constraintAnnotation.allowed()));

    if (validFields.isEmpty()) {
      Field[] fields = entityClass.getDeclaredFields();
      validFields = Arrays.stream(fields)
          .map(Field::getName)
          .collect(Collectors.toSet());
    }
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isBlank()) {
      return true;
    }

    return validFields.contains(value);
  }
}
