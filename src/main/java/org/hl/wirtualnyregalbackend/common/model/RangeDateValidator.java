package org.hl.wirtualnyregalbackend.common.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;

class RangeDateValidator implements ConstraintValidator<ValidRangeDate, RangeDate> {

    @Override
    public boolean isValid(RangeDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Instant startedAt = value.getStartedAt();
        Instant endedAt = value.getEndedAt();
        if (startedAt == null || endedAt == null) {
            return true;
        }
        return !startedAt.isAfter(endedAt);
    }

    @Override
    public void initialize(ValidRangeDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
