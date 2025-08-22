package org.hl.wirtualnyregalbackend.reading_session;

import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ReadingSessionRepository extends JpaRepository<ReadingSession, Long> {

    @Query("select rs from ReadingSession rs where rs.readingBook.bookshelf.user.id = :userId")
    Page<ReadingSession> findByUserId(Long userId, Pageable pageable);

    @Query("select rs from ReadingSession rs where rs.readingBook.bookshelf.user.id = :userId order by rs.createdAt desc limit 1")
    Optional<ReadingSession> findLastByUserId(Long userId);

}
