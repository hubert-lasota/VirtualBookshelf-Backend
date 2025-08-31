package org.hl.wirtualnyregalbackend.challenge_participant;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ChallengeParticipantRepository extends JpaRepository<ChallengeParticipant, Long> {

    @Query("select count(p) from ChallengeParticipant p where p.challenge.id = :challengeId")
    Long countByChallengeId(Long challengeId);

    @Query("select p from ChallengeParticipant p where p.challenge.id = :challengeId and p.user.id = :userId")
    Optional<ChallengeParticipant> findByChallengeIdAndUserId(Long challengeId, Long userId);

    Slice<ChallengeParticipant> findByUserAndStatus(User user, ChallengeParticipantStatus status, Pageable pageable);

    Slice<ChallengeParticipant> findByChallengeAndStatus(
        Challenge challenge,
        ChallengeParticipantStatus status,
        Pageable pageable
    );

}