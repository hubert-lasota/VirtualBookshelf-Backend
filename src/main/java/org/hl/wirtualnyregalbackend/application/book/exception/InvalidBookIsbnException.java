package org.hl.wirtualnyregalbackend.application.book.exception;

public class InvalidBookIsbnException extends RuntimeException {

    public InvalidBookIsbnException(String message) {
        super(message);
    }

}
