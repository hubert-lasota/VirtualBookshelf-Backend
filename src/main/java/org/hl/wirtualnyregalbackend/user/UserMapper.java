package org.hl.wirtualnyregalbackend.user;

import org.hl.wirtualnyregalbackend.security.model.User;
import org.hl.wirtualnyregalbackend.user.model.dto.UserHeaderResponse;

public class UserMapper {

    private UserMapper() { }

    public static UserHeaderResponse toUserHeaderResponse(User user) {
        String profilePictureUrl = user.getUserProfile().getProfilePicture().getProfilePictureUrl();
        return new UserHeaderResponse(user.getId(), user.getUsername(), profilePictureUrl);
    }

}
