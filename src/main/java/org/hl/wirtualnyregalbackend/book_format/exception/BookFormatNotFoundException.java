package org.hl.wirtualnyregalbackend.book_format.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookFormatNotFoundException extends RuntimeException {

    public BookFormatNotFoundException(Long bookFormatId) {
        super("Not found book format with id='%d'".formatted(bookFormatId));
    }

}
