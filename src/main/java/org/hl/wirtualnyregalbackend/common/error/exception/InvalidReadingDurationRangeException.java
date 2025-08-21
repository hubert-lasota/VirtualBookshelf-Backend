package org.hl.wirtualnyregalbackend.common.error.exception;

import lombok.Getter;
import org.hl.wirtualnyregalbackend.common.error.FieldValidationError;
import org.hl.wirtualnyregalbackend.common.model.ReadingDurationRange;

@Getter
public class InvalidReadingDurationRangeException extends RuntimeException {

    private final FieldValidationError error;

    public InvalidReadingDurationRangeException(ReadingDurationRange durationRange) {
        this.error = FieldValidationError.of("readingRange", "Invalid reading range. StartedAt must be before finished at", durationRange);
    }

}
