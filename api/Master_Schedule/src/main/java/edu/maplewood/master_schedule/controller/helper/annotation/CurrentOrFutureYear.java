package edu.maplewood.master_schedule.controller.helper.annotation;

import edu.maplewood.master_schedule.controller.helper.validator.YearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = YearValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentOrFutureYear {

  String message() default "Year must be the current year or a future year.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
