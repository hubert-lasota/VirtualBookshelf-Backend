package org.hl.wirtualnyregalbackend.challenge_participant;

import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface ChallengeParticipantRepository extends JpaRepository<ChallengeParticipant, Long> {

    @Query("select count(p) from ChallengeParticipant p where p.challenge.id = :challengeId")
    Long countByChallengeId(Long challengeId);

}