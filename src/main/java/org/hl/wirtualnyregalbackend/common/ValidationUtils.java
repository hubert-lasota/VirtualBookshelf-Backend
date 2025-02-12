package org.hl.wirtualnyregalbackend.common;

public class ValidationUtils {

    private ValidationUtils() { }

    public static String baseValidateString(String strToValidate, String fieldName) throws IllegalArgumentException {
        ActionResult result = validateStringAndReturnResult(strToValidate, fieldName);
        if(result.success()) {
            return strToValidate;
        } else {
            throw new IllegalArgumentException(result.error().message());
        }
    }

    public static boolean baseValidateString(String strToValidate) throws IllegalArgumentException {
        ActionResult result = validateStringAndReturnResult(strToValidate, "unknown");
        return result.success();
    }

    public static ActionResult validateStringAndReturnResult(String strToValidate, String fieldName) {
        if(strToValidate == null) {
            ApiError error = new ApiError(fieldName, "%s is null".formatted(fieldName));
            return new ActionResult(false, error);
        }
        if(strToValidate.isBlank()) {
            ApiError error = new ApiError(fieldName, "%s cannot be blank".formatted(fieldName));
            return new ActionResult(false, error);
        }
        return new ActionResult(true, null);
    }

}
