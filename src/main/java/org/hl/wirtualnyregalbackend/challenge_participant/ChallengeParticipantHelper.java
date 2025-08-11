package org.hl.wirtualnyregalbackend.challenge_participant;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;

@Component
@AllArgsConstructor
public class ChallengeParticipantHelper {

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

    public void createChallengeParticipant(Challenge challenge) {
        ChallengeParticipant participant = new ChallengeParticipant(challenge, Instant.now(clock), challenge.getUser());
        participantRepository.save(participant);
    }

    public void deleteParticipant(ChallengeParticipant participant) {
        participantRepository.delete(participant);
    }

}
