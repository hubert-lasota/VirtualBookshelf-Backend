package org.hl.wirtualnyregalbackend.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringConstrainsValidator.class)
public @interface StringConstraints {

    boolean allowBlank() default false;

    boolean allowNotTrimmed() default false;

    boolean allowWhitespace() default true;

    boolean allowMultipleSpacesBetweenWords() default false;

    String message() default "Invalid string";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
