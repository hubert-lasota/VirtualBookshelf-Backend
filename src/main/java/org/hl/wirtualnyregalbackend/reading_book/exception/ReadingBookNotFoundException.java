package org.hl.wirtualnyregalbackend.reading_book.exception;

public class ReadingBookNotFoundException extends RuntimeException {

    public ReadingBookNotFoundException(Long readingBookId) {
        super("ReadingBook with id='%d' not found".formatted(readingBookId));
    }

}
