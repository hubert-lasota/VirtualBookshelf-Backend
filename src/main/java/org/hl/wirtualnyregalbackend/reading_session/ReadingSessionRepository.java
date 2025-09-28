package org.hl.wirtualnyregalbackend.reading_session;

import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ReadingSessionRepository extends JpaRepository<ReadingSession, Long> {

    List<ReadingSession> findByReadingBookId(Long readingBookId);

    @Query("select rs from ReadingSession rs where rs.readingBook.bookshelf.user.id = :userId order by rs.createdAt desc limit 1")
    Optional<ReadingSession> findLastByUserId(Long userId);

    @Query("select count(rs) > 0 from ReadingSession rs where rs.id = :readingSessionId and rs.readingBook.bookshelf.user.id = :userId")
    boolean isAuthor(Long readingSessionId, Long userId);

}
