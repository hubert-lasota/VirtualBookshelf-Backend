package org.hl.wirtualnyregalbackend.reading_book.exception;

import lombok.Getter;
import org.hl.wirtualnyregalbackend.common.error.FieldValidationError;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;

@Getter
public class InvalidReadingBookDurationRangeException extends RuntimeException {

    private final FieldValidationError error;

    public InvalidReadingBookDurationRangeException(ReadingBookDurationRange durationRange) {
        this.error = FieldValidationError.of("durationRange", "Invalid duration range. StartedAt must be before FinishedAt.", durationRange);
    }

}
