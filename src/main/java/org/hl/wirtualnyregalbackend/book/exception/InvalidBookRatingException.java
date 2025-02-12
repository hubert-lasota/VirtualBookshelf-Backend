package org.hl.wirtualnyregalbackend.book.exception;

public class InvalidBookRatingException extends RuntimeException {
    public InvalidBookRatingException(String message) {
        super(message);
    }
}
