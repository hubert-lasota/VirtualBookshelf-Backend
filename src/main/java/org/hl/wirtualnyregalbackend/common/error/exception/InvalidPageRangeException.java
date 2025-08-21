package org.hl.wirtualnyregalbackend.common.error.exception;

import lombok.Getter;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.common.error.FieldValidationError;
import org.hl.wirtualnyregalbackend.common.model.PageRange;

@Getter
public class InvalidPageRangeException extends RuntimeException {

    private final FieldValidationError error;

    public InvalidPageRangeException(PageRange pageRange) {
        this.error = FieldValidationError.of("pageRange", "Invalid page range. From must be lower or equal to", pageRange);
    }

    public InvalidPageRangeException(PageRange pageRange, Book book) {
        this.error = FieldValidationError.of("pageRange", "Invalid page range. Field 'to' must be lower than page count(%d) of book(id='%d')".formatted(book.getId(), book.getPageCount()), pageRange);
    }

}
