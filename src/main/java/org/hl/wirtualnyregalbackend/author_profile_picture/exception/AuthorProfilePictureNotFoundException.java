package org.hl.wirtualnyregalbackend.author_profile_picture.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthorProfilePictureNotFoundException extends RuntimeException {

    public AuthorProfilePictureNotFoundException(Long pictureId) {
        super("Not found author profile picture with id='%d'".formatted(pictureId));
    }

}
