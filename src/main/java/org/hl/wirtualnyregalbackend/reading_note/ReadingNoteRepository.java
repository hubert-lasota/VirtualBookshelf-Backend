package org.hl.wirtualnyregalbackend.reading_note;

import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
interface ReadingNoteRepository extends JpaRepository<ReadingNote, Long>, JpaSpecificationExecutor<ReadingNote> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ReadingNote n WHERE n.readingBook.id = :readingBookId")
    void deleteByReadingBookId(Long readingBookId);

    @Query("select count(n) > 0 from ReadingNote n where n.id = :noteId and n.readingBook.bookshelf.user = :userId")
    boolean isAuthor(Long noteId, Long userId);

}
