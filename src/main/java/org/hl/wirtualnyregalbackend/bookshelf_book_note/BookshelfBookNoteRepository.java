package org.hl.wirtualnyregalbackend.bookshelf_book_note;

import org.hl.wirtualnyregalbackend.bookshelf_book_note.entity.BookshelfBookNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookshelfBookNoteRepository extends JpaRepository<BookshelfBookNote, Long> {
}
