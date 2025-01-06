package org.hl.wirtualnyregalbackend.infrastructure.author;

import org.hl.wirtualnyregalbackend.application.author.exception.IllegalAuthorOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthorExceptionHandler {

    @ExceptionHandler(IllegalAuthorOperationException.class)
    public ResponseEntity<?> handleIllegalAuthorOperationException(IllegalAuthorOperationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
        Long authorId = e.getAuthorId();
        if(authorId != null) {
            problemDetail.setProperty("authorId", authorId);
        }
        problemDetail.setProperty("operationType", e.getOperationType());
        return new ResponseEntity<>(problemDetail, status);
    }
}
