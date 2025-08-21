package org.hl.wirtualnyregalbackend.common.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLanguageCodeException extends RuntimeException {

    public InvalidLanguageCodeException(String message) {
        super(message);
    }

}
