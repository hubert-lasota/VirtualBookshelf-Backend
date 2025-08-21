package org.hl.wirtualnyregalbackend.author_review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthorReviewAlreadyExistsException extends RuntimeException {

    public AuthorReviewAlreadyExistsException(String message) {
        super(message);
    }

}
