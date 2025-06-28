package org.hl.wirtualnyregalbackend.bookshelf_book_note;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookshelfBookNoteService {

    private final BookshelfBookNoteRepository noteRepository;


}
