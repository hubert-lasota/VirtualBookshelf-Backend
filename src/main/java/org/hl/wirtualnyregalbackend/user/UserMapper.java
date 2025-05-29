package org.hl.wirtualnyregalbackend.user;

import org.hl.wirtualnyregalbackend.security.model.User;
import org.hl.wirtualnyregalbackend.security.model.dto.UserSignInResponseDto;
import org.hl.wirtualnyregalbackend.user.model.UserProfile;
import org.hl.wirtualnyregalbackend.user.model.UserProfilePicture;
import org.hl.wirtualnyregalbackend.user.model.dto.UserHeaderResponseDto;
import org.hl.wirtualnyregalbackend.user.model.dto.UserProfileDto;

public class UserMapper {

    private UserMapper() {
    }

    public static UserHeaderResponseDto toUserHeaderResponse(User user) {
        String profilePictureUrl = user.getUserProfile().getProfilePicture().getUrl();
        return new UserHeaderResponseDto(user.getId(), user.getUsername(), profilePictureUrl);
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
