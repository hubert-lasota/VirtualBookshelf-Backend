package org.hl.wirtualnyregalbackend.challenge_participant.event;

import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;

public record ChallengeParticipantCreatedEvent(Long participantId, Challenge challenge) {

    public static ChallengeParticipantCreatedEvent from(ChallengeParticipant participant) {
        return new ChallengeParticipantCreatedEvent(participant.getId(), participant.getChallenge());
    }

}
