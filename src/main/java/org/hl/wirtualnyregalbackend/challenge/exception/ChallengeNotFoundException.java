package org.hl.wirtualnyregalbackend.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChallengeNotFoundException extends RuntimeException {

    public ChallengeNotFoundException(Long challengeId) {
        super("Not found challenge with id='%d'".formatted(challengeId));
    }

}
