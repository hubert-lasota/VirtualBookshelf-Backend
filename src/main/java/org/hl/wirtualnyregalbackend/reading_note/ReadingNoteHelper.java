package org.hl.wirtualnyregalbackend.reading_note;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReadingNoteHelper {

    private final ReadingNoteRepository noteRepository;

    public Long getTotalNotes(Long readingBookId) {
        return noteRepository.countNotes(readingBookId);
    }

    public void deleteNotesByReadingBookId(Long readingBookId) {
        noteRepository.deleteNotesByReadingBookId(readingBookId);
    }

}
