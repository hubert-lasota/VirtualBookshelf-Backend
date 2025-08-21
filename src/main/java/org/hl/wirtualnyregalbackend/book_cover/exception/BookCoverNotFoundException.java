package org.hl.wirtualnyregalbackend.book_cover.exception;

public class BookCoverNotFoundException extends RuntimeException {

    public BookCoverNotFoundException(Long bookCoverId) {
        super("Not found book cover with id='%d'".formatted(bookCoverId));
    }

}
