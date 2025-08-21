package org.hl.wirtualnyregalbackend.common.error.exception;

import lombok.Getter;
import org.hl.wirtualnyregalbackend.common.error.FieldValidationError;
import org.hl.wirtualnyregalbackend.common.model.ReadingRange;

@Getter
public class InvalidReadingRangeException extends RuntimeException {

    private final FieldValidationError error;

    public InvalidReadingRangeException(ReadingRange readingRange) {
        this.error = FieldValidationError.of("readingRange", "Invalid reading range. StartedAt must be before finished at", readingRange);
    }

}
