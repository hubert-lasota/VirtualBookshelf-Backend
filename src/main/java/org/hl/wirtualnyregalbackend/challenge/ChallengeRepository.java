package org.hl.wirtualnyregalbackend.challenge;

import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface ChallengeRepository extends JpaRepository<Challenge, Long>, JpaSpecificationExecutor<Challenge> {

    @Query("select count(c) > 0 from Challenge c where c.id = :challengeId and c.user.id = :userId")
    boolean isChallengeAuthor(Long challengeId, Long userId);

}