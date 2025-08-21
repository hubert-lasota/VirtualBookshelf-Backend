package org.hl.wirtualnyregalbackend.challenge_participant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidChallengeParticipantStatusStateException extends RuntimeException {

    public InvalidChallengeParticipantStatusStateException(String message) {
        super(message);
    }

}
