package org.hl.wirtualnyregalbackend.challenge_participant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChallengeParticipantNotFoundException extends RuntimeException {

    public ChallengeParticipantNotFoundException(Long participantId) {
        super("Not found challenge participant with id='%d'".formatted(participantId));
    }

    public ChallengeParticipantNotFoundException(String message) {
        super(message);
    }

}
