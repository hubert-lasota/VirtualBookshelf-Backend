package org.hl.wirtualnyregalbackend.genre.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException(Long genreId) {
        super("Not found genre with id='%d'".formatted(genreId));
    }

}
