package org.hl.wirtualnyregalbackend.common.review;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class RatingValidator implements ConstraintValidator<Rating, Float> {

    private final List<Float> VALID_RATINGS = List.of(1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f, 4.5f, 5f);

    @Override
    public boolean isValid(Float value, ConstraintValidatorContext context) {
        if (value == null) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Rating must be one of %s".formatted(VALID_RATINGS))
            .addConstraintViolation();

        return VALID_RATINGS.contains(value);
    }

    @Override
    public void initialize(Rating constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

}
