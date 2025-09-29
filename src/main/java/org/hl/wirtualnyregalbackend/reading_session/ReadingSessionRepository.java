package org.hl.wirtualnyregalbackend.reading_session;

import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ReadingSessionRepository extends JpaRepository<ReadingSession, Long>, JpaSpecificationExecutor<ReadingSession> {

    @Query("select rs from ReadingSession rs where rs.readingBook.bookshelf.user.id = :userId and rs.id != :skipSessionId order by rs.createdAt desc limit 1")
    Optional<ReadingSession> findLastByUserIdAndNotSessionId(Long userId, Long skipSessionId);

    @Query("select count(rs) > 0 from ReadingSession rs where rs.id = :readingSessionId and rs.readingBook.bookshelf.user.id = :userId")
    boolean isAuthor(Long readingSessionId, Long userId);

}
