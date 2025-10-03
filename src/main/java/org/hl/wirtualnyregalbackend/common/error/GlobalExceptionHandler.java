package org.hl.wirtualnyregalbackend.common.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.hl.wirtualnyregalbackend.common.reading.InvalidPageRangeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
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
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Failed");
        List<FieldValidationError> errors = ex
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .map(err -> FieldValidationError.of(err.getField(), err.getDefaultMessage(), err.getRejectedValue()))
            .toList();

        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    @ExceptionHandler(InvalidPageRangeException.class)
    public ProblemDetail handleInvalidPageRangeException(InvalidPageRangeException ex) {
        List<FieldValidationError> errors = List.of(ex.getError());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Failed");
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

}
