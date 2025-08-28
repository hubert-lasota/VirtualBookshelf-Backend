package org.hl.wirtualnyregalbackend.reading_note;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ReadingNoteHelper {

    private final ReadingNoteRepository noteRepository;

    public void deleteNotesByReadingBookId(Long readingBookId) {
        log.info("Deleting notes for reading book with ID: {}", readingBookId);
        noteRepository.deleteNotesByReadingBookId(readingBookId);
    }

}
