package org.hl.wirtualnyregalbackend.user;

import org.hl.wirtualnyregalbackend.security.model.User;
import org.hl.wirtualnyregalbackend.user.model.dto.UserHeaderResponseDto;

public class UserMapper {

    private UserMapper() {
    }

    public static UserHeaderResponseDto toUserHeaderResponse(User user) {
        String profilePictureUrl = user.getUserProfile().getProfilePicture().getUrl();
        return new UserHeaderResponseDto(user.getId(), user.getUsername(), profilePictureUrl);
    }

}
