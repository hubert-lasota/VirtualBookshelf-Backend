package org.hl.wirtualnyregalbackend.common.review;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RatingValidator.class)
public @interface Rating {

    String message() default "Invalid rating";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
