package org.hl.wirtualnyregalbackend.infrastructure.book;

import org.hl.wirtualnyregalbackend.application.book.exception.IllegalBookOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(IllegalBookOperationException.class)
    public ResponseEntity<?> handleIllegalBookOperationException(IllegalBookOperationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
        problemDetail.setProperty("bookId", e.getBookId());
        problemDetail.setProperty("operationType", e.getOperationType());
        return new ResponseEntity<>(problemDetail, status);
    }

}
