package org.hl.wirtualnyregalbackend.challenge_participant.event;

import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;

public record ChallengeParticipantDeletedEvent(Long participantId, Challenge challenge) {

    public static ChallengeParticipantDeletedEvent from(ChallengeParticipant participant) {
        return new ChallengeParticipantDeletedEvent(participant.getId(), participant.getChallenge());
    }

}
