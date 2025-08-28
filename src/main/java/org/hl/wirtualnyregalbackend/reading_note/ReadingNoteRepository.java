package org.hl.wirtualnyregalbackend.reading_note;

import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
interface ReadingNoteRepository extends JpaRepository<ReadingNote, Long> {

    @Query("select note from ReadingNote note where note.readingBook.id = :readingBookId")
    List<ReadingNote> findReadingNotesByReadingBookId(Long readingBookId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ReadingNote n WHERE n.readingBook.id = :readingBookId")
    void deleteNotesByReadingBookId(Long readingBookId);

}
