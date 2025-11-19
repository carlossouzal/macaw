package edu.maplewood.master_schedule.controller.helper.annotation;

import edu.maplewood.master_schedule.controller.helper.validator.SortByValidator;
import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SortByValidator.class)
public @interface ValidSortBy {

  String message() default "Invalid sortBy parameter";

  Class<?>[] groups() default {};

  Class<?>[] payload() default {};

  Class<?> entity();

  String[] allowed() default {};
}
