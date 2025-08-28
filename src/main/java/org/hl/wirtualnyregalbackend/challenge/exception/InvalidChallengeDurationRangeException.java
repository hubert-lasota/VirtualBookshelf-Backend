package org.hl.wirtualnyregalbackend.challenge.exception;

import lombok.Getter;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeDurationRange;
import org.hl.wirtualnyregalbackend.common.error.FieldValidationError;

@Getter
public class InvalidChallengeDurationRangeException extends RuntimeException {

    private final FieldValidationError error;

    public InvalidChallengeDurationRangeException(ChallengeDurationRange invalidDurationRange) {
        this.error = FieldValidationError.of("durationRange", "Invalid duration range. StartAt must be before EndAt.", invalidDurationRange);
    }

}
