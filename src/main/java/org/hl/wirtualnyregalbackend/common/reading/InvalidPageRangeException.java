package org.hl.wirtualnyregalbackend.common.reading;

import lombok.Getter;
import org.hl.wirtualnyregalbackend.common.error.FieldValidationError;

@Getter
public class InvalidPageRangeException extends RuntimeException {

    private final FieldValidationError error;

    public InvalidPageRangeException(PageRange rejectedPageRange) {
        this("Invalid page range. From must be lower or equal to", rejectedPageRange);
    }

    public InvalidPageRangeException(String message, PageRange rejectedPageRange) {
        this.error = FieldValidationError.of("pageRange", message, rejectedPageRange);
    }

}
