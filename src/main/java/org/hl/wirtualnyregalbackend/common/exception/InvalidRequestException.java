package org.hl.wirtualnyregalbackend.common.exception;

import org.hl.wirtualnyregalbackend.common.model.ApiError;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class InvalidRequestException extends RuntimeException {

    private final List<ApiError> errors;
    private final HttpStatus httpStatus;

    public InvalidRequestException(List<ApiError> errors, String message, HttpStatus httpStatus) {
        super(message);
        this.errors = errors == null ? Collections.emptyList() : errors;
        this.httpStatus = httpStatus;
    }

    public InvalidRequestException(List<ApiError> errors, String message) {
        this(errors, message, HttpStatus.BAD_REQUEST);
    }

    public InvalidRequestException(String message) {
        this(Collections.emptyList(), message, HttpStatus.BAD_REQUEST);
    }

    public InvalidRequestException(String message, HttpStatus httpStatus) {
        this(Collections.emptyList(), message, httpStatus);
    }


    public List<ApiError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
