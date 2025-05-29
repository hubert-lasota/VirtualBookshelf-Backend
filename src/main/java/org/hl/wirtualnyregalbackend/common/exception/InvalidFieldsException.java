package org.hl.wirtualnyregalbackend.common.exception;

import org.hl.wirtualnyregalbackend.common.model.ApiError;

import java.util.Collections;
import java.util.List;

public class InvalidFieldsException extends RuntimeException {

    private final List<ApiError> errors;

    public InvalidFieldsException(List<ApiError> errors) {
        this.errors = errors;
    }

    public List<ApiError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

}
