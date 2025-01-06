package org.hl.wirtualnyregalbackend.application.book.exception;

public class InvalidBookRatingException extends RuntimeException {
    public InvalidBookRatingException(String message) {
        super(message);
    }
}
