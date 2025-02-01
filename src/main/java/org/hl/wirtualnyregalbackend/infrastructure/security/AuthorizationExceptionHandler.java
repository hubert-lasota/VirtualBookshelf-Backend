package org.hl.wirtualnyregalbackend.infrastructure.security;

import org.hl.wirtualnyregalbackend.infrastructure.security.exception.PermissionDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
class AuthorizationExceptionHandler {

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
