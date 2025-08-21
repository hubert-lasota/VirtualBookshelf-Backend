package org.hl.wirtualnyregalbackend.book_cover.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookCoverNotFoundException extends RuntimeException {

    public BookCoverNotFoundException(Long bookCoverId) {
        super("Not found book cover with id='%d'".formatted(bookCoverId));
    }

}
