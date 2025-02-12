package org.hl.wirtualnyregalbackend.common.exception;

import org.hl.wirtualnyregalbackend.common.ActionType;
import org.hl.wirtualnyregalbackend.common.ApiError;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class InvalidRequestException extends RuntimeException {

    private final List<ApiError> errors;
    private final ActionType actionType;
    private final HttpStatus httpStatus;

    public InvalidRequestException(List<ApiError> errors, ActionType actionType, String message, HttpStatus httpStatus) {
        super(message);
        this.errors = errors == null ? Collections.emptyList() : errors;
        this.actionType = actionType;
        this.httpStatus = httpStatus;
    }

    public InvalidRequestException(List<ApiError> errors, ActionType actionType, String message) {
        this(errors, actionType, message, HttpStatus.BAD_REQUEST);
    }

    public List<ApiError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public ActionType getActionType() {
        return actionType;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
