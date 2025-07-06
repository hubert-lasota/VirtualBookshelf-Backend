package org.hl.wirtualnyregalbackend.bookshelf_book_note;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookshelfBookNoteHelper {

    private final BookshelfBookNoteRepository noteRepository;

    public Long getTotalNotes(Long bookshelfBookId) {
        return noteRepository.countNotes(bookshelfBookId);
    }

    public void deleteNotesByBookshelfBookId(Long bookshelfBookId) {
        noteRepository.deleteNotesByBookshelfBookId(bookshelfBookId);
    }

}
