package org.hl.wirtualnyregalbackend.application.bookshelf.exception;

public class BookshelfNotFoundException extends RuntimeException {

    public BookshelfNotFoundException(String message) {
        super(message);
    }

}
