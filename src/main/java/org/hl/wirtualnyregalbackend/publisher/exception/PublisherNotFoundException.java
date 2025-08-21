package org.hl.wirtualnyregalbackend.publisher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PublisherNotFoundException extends RuntimeException {

    public PublisherNotFoundException(Long publisherId) {
        super("Not found publisher with id='%d'".formatted(publisherId));
    }

}
