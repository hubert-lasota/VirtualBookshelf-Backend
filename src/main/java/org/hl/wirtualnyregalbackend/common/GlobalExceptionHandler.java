package org.hl.wirtualnyregalbackend.common;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.exception.InvalidFieldsException;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String errorMessage = "Cannot deserialize incorrect JSON";
        if (ex.getCause() instanceof InvalidFormatException cause) {
            if (cause.getTargetType().isEnum()) {
                String availableValues = Arrays.toString(cause.getTargetType().getEnumConstants());
                String fieldName = cause.getPath().isEmpty() ? "unknown field" :
                    cause.getPath().get(cause.getPath().size() - 1).getFieldName();
                errorMessage = "Invalid value for field '%s'. Allowed values: %s"
                    .formatted(fieldName, availableValues);
            }
        }
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage);
        return ResponseEntity.badRequest().body(detail);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> handleInvalidRequestException(InvalidRequestException ex) {
        HttpStatus status = ex.getHttpStatus();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getErrors().forEach(apiError -> errors.put(apiError.field(), apiError.message()));
        problemDetail.setProperty("errors", errors);
        return new ResponseEntity<>(problemDetail, status);
    }


    @ExceptionHandler(InvalidFieldsException.class)
    public ResponseEntity<?> handleInvalidFieldsException(InvalidFieldsException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getErrors().forEach(apiError -> errors.put(apiError.field(), apiError.message()));
        problemDetail.setProperty("errors", errors);
        return new ResponseEntity<>(problemDetail, status);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        return new ResponseEntity<>(problemDetail, status);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        return new ResponseEntity<>(problemDetail, status);
    }

}
