package org.hl.wirtualnyregalbackend.reading_session.exception;

import lombok.Getter;
import org.hl.wirtualnyregalbackend.common.error.FieldValidationError;
import org.hl.wirtualnyregalbackend.reading_session.model.SessionReadingDurationRange;

@Getter
public class InvalidSessionReadingDurationRangeException extends RuntimeException {

    private final FieldValidationError error;

    public InvalidSessionReadingDurationRangeException(SessionReadingDurationRange durationRange) {
        this.error = FieldValidationError.of("readingRange", "Invalid reading range. StartedAt must be before finished at", durationRange);
    }

}
