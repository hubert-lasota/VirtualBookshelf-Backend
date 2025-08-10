package org.hl.wirtualnyregalbackend.challenge_participant;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChallengeParticipantHelper {

    private final ChallengeParticipantRepository participantRepository;

    public Long findTotalParticipantsByChallengeId(Long challengeId) {
        return participantRepository.countByChallengeId(challengeId);
    }

}
