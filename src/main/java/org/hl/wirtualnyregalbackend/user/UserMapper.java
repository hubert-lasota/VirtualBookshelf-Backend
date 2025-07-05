package org.hl.wirtualnyregalbackend.user;

import org.hl.wirtualnyregalbackend.security.dto.UserSignInResponseDto;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.hl.wirtualnyregalbackend.user.dto.UserHeaderResponseDto;
import org.hl.wirtualnyregalbackend.user.dto.UserProfileDto;
import org.hl.wirtualnyregalbackend.user.entity.UserProfile;
import org.hl.wirtualnyregalbackend.user.entity.UserProfilePicture;

public class UserMapper {

    private UserMapper() {
    }

    public static UserHeaderResponseDto toUserHeaderResponseDto(User user) {
        UserProfileDto profile = toUserProfileDto(user.getUserProfile());
        return new UserHeaderResponseDto(user.getId(), user.getUsername(), profile);
    }

    public static UserSignInResponseDto toUserSignInResponseDto(User user, String jwt) {
        UserProfile profile = user.getUserProfile();
        UserProfileDto profileDto = profile != null ? UserMapper.toUserProfileDto(user.getUserProfile()) : null;
        return new UserSignInResponseDto(
            user.getId(),
            user.getUsername(),
            jwt,
            profileDto
        );
    }

    public static UserProfileDto toUserProfileDto(UserProfile profile) {
        UserProfilePicture profilePicture = profile.getProfilePicture();
        String pictureUrl = profilePicture != null ? profilePicture.getUrl() : null;
        return new UserProfileDto(
            profile.getFirstName(),
            profile.getLastName(),
            pictureUrl,
            profile.getDescription()
        );
    }

}
