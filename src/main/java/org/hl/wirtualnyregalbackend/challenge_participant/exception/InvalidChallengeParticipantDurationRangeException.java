package org.hl.wirtualnyregalbackend.challenge_participant.exception;

import lombok.Getter;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantDurationRange;
import org.hl.wirtualnyregalbackend.common.error.FieldValidationError;

@Getter
public class InvalidChallengeParticipantDurationRangeException extends RuntimeException {

    private final FieldValidationError error;

    public InvalidChallengeParticipantDurationRangeException(ChallengeParticipantDurationRange durationRange) {
        this.error = FieldValidationError.of("durationRange", "Invalid duration range. StartedAt must be before FinishedAt.", durationRange);
    }

}
