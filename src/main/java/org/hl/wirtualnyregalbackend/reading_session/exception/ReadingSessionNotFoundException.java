package org.hl.wirtualnyregalbackend.reading_session.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReadingSessionNotFoundException extends RuntimeException {

    public ReadingSessionNotFoundException(Long readingSessionId) {
        super("Not found reading session with id='%d'".formatted(readingSessionId));
    }

}
