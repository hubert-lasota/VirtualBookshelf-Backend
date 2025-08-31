package org.hl.wirtualnyregalbackend.common.review;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RatingValidator implements ConstraintValidator<Rating, Float> {

    private final ReviewProperties reviewProperties;

    @Override
    public boolean isValid(Float value, ConstraintValidatorContext context) {
        if (value == null) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Rating must be one of %s".formatted(reviewProperties.validRatings()))
            .addConstraintViolation();

        return reviewProperties.validRatings().contains(value);
    }

    @Override
    public void initialize(Rating constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

}
