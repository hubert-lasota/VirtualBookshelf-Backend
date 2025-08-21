package org.hl.wirtualnyregalbackend.reading_note.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReadingNoteNotFoundException extends RuntimeException {

    public ReadingNoteNotFoundException(Long readingNoteId) {
        super("Not found reading note with id='%d'".formatted(readingNoteId));
    }

}
