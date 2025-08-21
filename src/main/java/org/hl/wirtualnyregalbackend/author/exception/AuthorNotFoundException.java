package org.hl.wirtualnyregalbackend.author.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(Long authorId) {
        super("Not found author with id='%d'".formatted(authorId));
    }

}
