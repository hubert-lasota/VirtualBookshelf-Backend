package org.hl.wirtualnyregalbackend.common.exception;

import lombok.Getter;
import org.hl.wirtualnyregalbackend.common.model.ApiFieldError;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
public class InvalidRequestException extends RuntimeException {

    private final List<ApiFieldError> errors;
    private final HttpStatus httpStatus;

    public InvalidRequestException(List<ApiFieldError> errors, String message, HttpStatus httpStatus) {
        super(message);
        this.errors = errors == null ? Collections.emptyList() : errors;
        this.httpStatus = httpStatus;
    }

    public InvalidRequestException(String message) {
        this(Collections.emptyList(), message, HttpStatus.BAD_REQUEST);
    }

    public InvalidRequestException(String message, HttpStatus httpStatus) {
        this(Collections.emptyList(), message, httpStatus);
    }


    public List<ApiFieldError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

}
