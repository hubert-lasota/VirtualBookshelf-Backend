package org.hl.wirtualnyregalbackend.common.error;

public record FieldValidationError(
    String field,
    String message,
    Object rejectedValue,
    ErrorType errorType
) {

    public static FieldValidationError of(String field, String message, Object rejectedValue) {
        return new FieldValidationError(field, message, rejectedValue, ErrorType.VALIDATION_FAILED);
    }

}
