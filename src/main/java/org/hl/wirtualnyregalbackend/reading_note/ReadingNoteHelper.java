package org.hl.wirtualnyregalbackend.reading_note;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReadingNoteHelper {

    private final ReadingNoteRepository noteRepository;

    public Long getTotalNotes(Long bookshelfBookId) {
        return noteRepository.countNotes(bookshelfBookId);
    }

    public void deleteNotesByBookshelfBookId(Long bookshelfBookId) {
        noteRepository.deleteNotesByBookshelfBookId(bookshelfBookId);
    }

}
