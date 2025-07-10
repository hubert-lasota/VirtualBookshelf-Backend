package org.hl.wirtualnyregalbackend.reading_session;

import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ReadingSessionRepository extends JpaRepository<ReadingSession, Long> {

    @Query("select rs from ReadingSession rs where rs.readingBook.bookshelf.user.id = :userId")
    List<ReadingSession> findByUserId(Long userId);

}
