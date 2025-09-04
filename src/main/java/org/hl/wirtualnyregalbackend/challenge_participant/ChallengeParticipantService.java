package org.hl.wirtualnyregalbackend.challenge_participant;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantDurationRange;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

@Service
@AllArgsConstructor
public class ChallengeParticipantService {

    private final ChallengeParticipantRepository participantRepository;
    private final Clock clock;

    public Long findTotalParticipantsByChallengeId(Long challengeId) {
        return participantRepository.countByChallengeId(challengeId);
    }

    @Nullable
    public ChallengeParticipant findCurrentUserParticipant(Long challengeId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return participantRepository.findByChallengeIdAndUserId(challengeId, user.getId())
            .orElse(null);
    }

    @Transactional
    public ChallengeParticipant createChallengeParticipant(Challenge challenge) {
        var durationRange = new ChallengeParticipantDurationRange(Instant.now(clock), null);
        ChallengeParticipant participant = new ChallengeParticipant(
            0,
            ChallengeParticipantStatus.ACTIVE,
            durationRange,
            challenge,
            challenge.getUser()
        );
        return participantRepository.save(participant);
    }

    public void deleteParticipant(ChallengeParticipant participant) {
        participantRepository.delete(participant);
    }

}
