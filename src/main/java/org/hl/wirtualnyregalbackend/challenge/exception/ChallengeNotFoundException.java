package org.hl.wirtualnyregalbackend.challenge.exception;

public class ChallengeNotFoundException extends RuntimeException {

    public ChallengeNotFoundException(Long challengeId) {
        super("Not found challenge with id='%d'".formatted(challengeId));
    }

}
