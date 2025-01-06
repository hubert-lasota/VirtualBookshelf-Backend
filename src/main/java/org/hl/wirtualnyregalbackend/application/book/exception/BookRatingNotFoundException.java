package org.hl.wirtualnyregalbackend.application.book.exception;

public class BookRatingNotFoundException extends RuntimeException {

    public BookRatingNotFoundException(String message) {
        super(message);
    }

}
