package org.hl.wirtualnyregalbackend.application.common;

import java.util.Objects;

public class ValidationUtils {

    protected ValidationUtils() { }

    public static String baseValidateString(String strToValidate, String fieldName)
            throws IllegalArgumentException, NullPointerException {
        Objects.requireNonNull(strToValidate, "%s cannot be null".formatted(fieldName));
        if(strToValidate.isBlank()) {
            throw new IllegalArgumentException("%s cannot be blank".formatted(fieldName));
        }
        return strToValidate;
    }

    public static boolean baseValidateString(String str) {
        return str != null && !str.isBlank();
    }

}
