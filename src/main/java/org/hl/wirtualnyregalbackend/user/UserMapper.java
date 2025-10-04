package org.hl.wirtualnyregalbackend.user;

import org.hl.wirtualnyregalbackend.auth.dto.UserSignInResponse;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.user.dto.UserProfileDto;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;
import org.hl.wirtualnyregalbackend.user.entity.UserProfile;
import org.hl.wirtualnyregalbackend.user_profile_picture.entity.UserProfilePicture;

public class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toUserResponse(User user) {
        UserProfileDto profile = toUserProfileDto(user.getUserProfile());
        return new UserResponse(user.getId(), user.getUsername(), profile);
    }

    public static UserSignInResponse toUserSignInResponse(User user, String jwt) {
        UserProfile profile = user.getUserProfile();
        UserProfileDto profileDto = profile != null ? toUserProfileDto(user.getUserProfile()) : null;
        return new UserSignInResponse(
            user.getId(),
            user.getUsername(),
            jwt,
            profileDto
        );
    }

    private static UserProfileDto toUserProfileDto(UserProfile profile) {
        UserProfilePicture profilePicture = profile.getProfilePicture();
        String pictureUrl = profilePicture != null ? profilePicture.getUrl() : null;
        return new UserProfileDto(
            profile.getFirstName(),
            profile.getLastName(),
            profile.getDescription(),
            pictureUrl
        );
    }

    public static UserProfile toUserProfile(UserProfileDto profileDto, User user, UserProfilePicture profilePicture) {
        return new UserProfile(
            profileDto.firstName(),
            profileDto.lastName(),
            profileDto.description(),
            user,
            profilePicture
        );
    }

}
