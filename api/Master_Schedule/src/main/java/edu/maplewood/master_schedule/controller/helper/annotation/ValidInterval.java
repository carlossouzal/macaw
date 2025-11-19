package edu.maplewood.master_schedule.controller.helper.annotation;

import edu.maplewood.master_schedule.controller.helper.validator.IntervalValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ValidIntervals.class)
@Constraint(validatedBy = IntervalValidator.class)
public @interface ValidInterval {

  String message() default "Invalid interval: max must be greater than or equal to min";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String minField();

  String maxField();
}

