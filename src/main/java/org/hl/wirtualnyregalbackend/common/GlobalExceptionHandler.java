package org.hl.wirtualnyregalbackend.common;

import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> handleInvalidRequestException(InvalidRequestException ex) {
        HttpStatus status = ex.getHttpStatus();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setProperty("error", ex.getErrors());
        problemDetail.setProperty("action", ex.getActionType());
        return new ResponseEntity<>(problemDetail, status);
    }

}
