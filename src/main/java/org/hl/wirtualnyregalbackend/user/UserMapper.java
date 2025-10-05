package org.hl.wirtualnyregalbackend.user;

import org.hl.wirtualnyregalbackend.auth.dto.UserSignInResponse;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.genre.GenreMapper;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
import org.hl.wirtualnyregalbackend.recommendation.entity.UserGenrePreferences;
import org.hl.wirtualnyregalbackend.user.dto.UserProfileDto;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;
import org.hl.wirtualnyregalbackend.user.entity.UserProfile;
import org.hl.wirtualnyregalbackend.user_profile_picture.entity.UserProfilePicture;

import java.util.List;
import java.util.Locale;

public class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toUserResponse(User user, Locale locale) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            toUserProfileDto(user.getUserProfile()),
            toGenreResponseList(user, locale)
        );
    }

    public static UserSignInResponse toUserSignInResponse(User user, String jwt, Locale locale) {
        return new UserSignInResponse(
            user.getId(),
            user.getUsername(),
            jwt,
            toUserProfileDto(user.getUserProfile()),
            toGenreResponseList(user, locale)
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

    private static List<GenreResponse> toGenreResponseList(User user, Locale locale) {
        return user
            .getGenrePreferences()
            .stream()
            .map(UserGenrePreferences::getGenre)
            .map(genre -> GenreMapper.toGenreResponse(genre, locale))
            .toList();
    }

}
