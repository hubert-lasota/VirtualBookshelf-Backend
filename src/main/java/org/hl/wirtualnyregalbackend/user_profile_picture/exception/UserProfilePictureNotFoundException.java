package org.hl.wirtualnyregalbackend.user_profile_picture.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserProfilePictureNotFoundException extends RuntimeException {

    public UserProfilePictureNotFoundException(Long pictureId) {
        super("Not found user profile picture with id='%d'".formatted(pictureId));
    }

}
