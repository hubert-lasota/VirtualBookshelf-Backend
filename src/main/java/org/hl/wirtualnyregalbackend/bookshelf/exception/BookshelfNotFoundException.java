package org.hl.wirtualnyregalbackend.bookshelf.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookshelfNotFoundException extends RuntimeException {

    public BookshelfNotFoundException(Long bookshelfId) {
        super("Not found bookshelf with id='%d'".formatted(bookshelfId));
    }

}
