package org.hl.wirtualnyregalbackend.infrastructure.security;

import org.hl.wirtualnyregalbackend.infrastructure.security.exception.InvalidSignInCredentialsException;
import org.hl.wirtualnyregalbackend.infrastructure.security.exception.PermissionDeniedException;
import org.hl.wirtualnyregalbackend.infrastructure.security.exception.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
class AuthorizationExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
        return new ResponseEntity<>(problemDetail, status);
    }

    @ExceptionHandler(InvalidSignInCredentialsException.class)
    public ResponseEntity<?> handleInvalidSignInCredentialsException() {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(status, "Login failed due to invalid credentials.");
        return new ResponseEntity<>(problemDetail, status);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<?> handlePermissionDeniedException(PermissionDeniedException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "Permission denied.");
        Map<String, Object> properties = new HashMap<>();
        properties.put("resourceType", e.getResourceType());
        properties.put("actionType", e.getActionType());
        problemDetail.setProperties(properties);
        return new ResponseEntity<>(problemDetail, status);
    }

}
