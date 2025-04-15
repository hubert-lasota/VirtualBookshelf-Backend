package org.hl.wirtualnyregalbackend.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class NotAllFieldsNullValidator implements ConstraintValidator<NotAllFieldsNull, Object> {

    @Override
    public void initialize(NotAllFieldsNull constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Field[] fields = value.getClass().getDeclaredFields();
        boolean anyNotNull = false;
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object fieldValue = field.get(value);
                if (fieldValue != null) {
                    anyNotNull = true;
                    break;
                }
            } catch (IllegalAccessException e) {
                return false;
            }
        }

        return anyNotNull;
    }

}
