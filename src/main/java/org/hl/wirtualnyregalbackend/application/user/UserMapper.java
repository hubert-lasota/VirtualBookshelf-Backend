package org.hl.wirtualnyregalbackend.application.user;

import org.hl.wirtualnyregalbackend.infrastructure.security.User;
import org.hl.wirtualnyregalbackend.infrastructure.user.dto.UserHeaderResponse;

public class UserMapper {

    private UserMapper() { }

    public static UserHeaderResponse toUserHeaderResponse(User user) {
        String profilePictureUrl = user.getProfilePicture().getProfilePictureUrl();
        return new UserHeaderResponse(user.getId(), user.getUsername(), profilePictureUrl);
    }
}
