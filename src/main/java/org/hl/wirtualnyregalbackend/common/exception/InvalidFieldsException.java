package org.hl.wirtualnyregalbackend.common.exception;

import org.hl.wirtualnyregalbackend.common.model.ApiFieldError;

import java.util.Collections;
import java.util.List;

public class InvalidFieldsException extends RuntimeException {

    private final List<ApiFieldError> errors;

    public InvalidFieldsException(List<ApiFieldError> errors) {
        this.errors = errors;
    }

    public List<ApiFieldError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

}
