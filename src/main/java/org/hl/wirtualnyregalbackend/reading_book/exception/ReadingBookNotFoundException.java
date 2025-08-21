package org.hl.wirtualnyregalbackend.reading_book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReadingBookNotFoundException extends RuntimeException {

    public ReadingBookNotFoundException(Long readingBookId) {
        super("ReadingBook with id='%d' not found".formatted(readingBookId));
    }

}
