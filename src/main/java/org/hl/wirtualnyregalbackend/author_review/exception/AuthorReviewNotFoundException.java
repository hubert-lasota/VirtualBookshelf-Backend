package org.hl.wirtualnyregalbackend.author_review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthorReviewNotFoundException extends RuntimeException {

    public AuthorReviewNotFoundException(Long authorReviewId) {
        super("Not found author review with id='%d'".formatted(authorReviewId));
    }

}
