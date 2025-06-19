package org.hl.wirtualnyregalbackend.common.model;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangeDateValidator.class)
@interface ValidRangeDate {

    String message() default "Invalid Range Date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
