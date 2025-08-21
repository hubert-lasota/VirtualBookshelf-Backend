package org.hl.wirtualnyregalbackend.book_review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookReviewNotFoundException extends RuntimeException {

    public BookReviewNotFoundException(Long bookReviewId) {
        super("Not found book review with id='%d'".formatted(bookReviewId));
    }

}
