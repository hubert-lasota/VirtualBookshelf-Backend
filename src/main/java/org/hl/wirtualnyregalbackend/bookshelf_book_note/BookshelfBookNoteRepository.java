package org.hl.wirtualnyregalbackend.bookshelf_book_note;

import org.hl.wirtualnyregalbackend.bookshelf_book_note.entity.BookshelfBookNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BookshelfBookNoteRepository extends JpaRepository<BookshelfBookNote, Long> {

    @Query("select note from BookshelfBookNote note where note.bookshelfBook.id = :bookshelfBookId")
    List<BookshelfBookNote> findBookshelfBookNotesByBookshelfBookId(Long bookshelfBookId);

    @Query("select count(n) from BookshelfBookNote n where n.bookshelfBook.id = :bookshelfBookId")
    Long countNotes(Long bookshelfBookId);

}
