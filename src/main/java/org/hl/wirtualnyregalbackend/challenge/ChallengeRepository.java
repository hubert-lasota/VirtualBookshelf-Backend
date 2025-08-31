package org.hl.wirtualnyregalbackend.challenge;

import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
interface ChallengeRepository extends JpaRepository<Challenge, Long>, JpaSpecificationExecutor<Challenge> {

    @Query("select count(c) > 0 from Challenge c where c.id = :challengeId and c.user.id = :userId")
    boolean isChallengeAuthor(Long challengeId, Long userId);

    @Query("""
        select c from Challenge c
        where function('DATE_TRUNC', 'day', c.durationRange.endAt) = :endAt
        """)
    Slice<Challenge> findByEndAt(LocalDate endAt, Pageable pageable);

}