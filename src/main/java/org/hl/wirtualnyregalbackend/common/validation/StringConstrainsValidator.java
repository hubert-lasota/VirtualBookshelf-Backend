package org.hl.wirtualnyregalbackend.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StringConstrainsValidator implements ConstraintValidator<StringConstraints, String> {
    private boolean allowBlank;
    private boolean allowNotTrimmed;
    private boolean allowWhitespace;
    private boolean allowMultipleSpacesBetweenWords;

    @Override
    public void initialize(StringConstraints constraintAnnotation) {
        this.allowBlank = constraintAnnotation.allowBlank();
        this.allowNotTrimmed = constraintAnnotation.allowNotTrimmed();
        this.allowWhitespace = constraintAnnotation.allowWhitespace();
        this.allowMultipleSpacesBetweenWords = constraintAnnotation.allowMultipleSpacesBetweenWords();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        context.disableDefaultConstraintViolation();
        if (!allowBlank && value.isBlank()) {
            context.buildConstraintViolationWithTemplate("Value cannot be blank")
                .addConstraintViolation();
            return false;
        }

        if (!allowNotTrimmed) {
            boolean startsWithWhitespace = Character.isWhitespace(value.charAt(0));
            boolean endsWithWhitespace = Character.isWhitespace(value.charAt(value.length() - 1));
            if (startsWithWhitespace || endsWithWhitespace) {
                context.buildConstraintViolationWithTemplate("Value cannot be surrounded by whitespace")
                    .addConstraintViolation();
                return false;
            }
        }

        if (!allowWhitespace) {
            String trimmedValue = value.trim();
            if (trimmedValue.length() != value.length()) {
                context.buildConstraintViolationWithTemplate("Value cannot contain whitespace")
                    .addConstraintViolation();
                return false;
            }
        }

        if (!allowMultipleSpacesBetweenWords && value.matches(".*\\b\\s{2,}\\b.*")) {
            context.buildConstraintViolationWithTemplate("Value cannot contain more than one space between words")
                .addConstraintViolation();
            return false;

        }

        return true;
    }
}
