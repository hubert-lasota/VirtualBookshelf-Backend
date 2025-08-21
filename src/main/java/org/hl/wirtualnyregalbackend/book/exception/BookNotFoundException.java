package org.hl.wirtualnyregalbackend.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long bookId) {
        super("Not found book with id='%d'".formatted(bookId));
    }

}
