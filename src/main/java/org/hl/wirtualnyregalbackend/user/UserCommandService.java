package org.hl.wirtualnyregalbackend.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.genre.GenreQueryService;
import org.hl.wirtualnyregalbackend.recommendation.entity.UserGenrePreferences;
import org.hl.wirtualnyregalbackend.user.dto.UpdateGenrePreferencesRequest;
import org.hl.wirtualnyregalbackend.user.dto.UserProfileDto;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;
import org.hl.wirtualnyregalbackend.user.entity.UserProfile;
import org.hl.wirtualnyregalbackend.user_profile_picture.UserProfilePictureService;
import org.hl.wirtualnyregalbackend.user_profile_picture.entity.UserProfilePicture;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class UserCommandService {

    private final UserRepository repository;
    private final UserProfilePictureService pictureService;
    private final GenreQueryService genreQuery;

    @Transactional
    UserResponse uploadProfilePicture(User user, MultipartFile profilePicture) {
        UserProfilePicture profilePic = pictureService.createUserProfilePicture(null, profilePicture);
        user.getUserProfile().setProfilePicture(profilePic);
        return mapToUserProfileResponse(user);
    }

    @Transactional
    UserResponse updateGenrePreferences(User user, UpdateGenrePreferencesRequest request) {
        List<UserGenrePreferences> genres = genreQuery
            .findGenresByIds(request.genrePreferenceIds())
            .stream()
            .map((genre) -> new UserGenrePreferences(user, genre))
            .toList();
        user.setGenrePreferences(genres);
        return mapToUserProfileResponse(user);
    }

    @Transactional
    UserResponse updateUserProfile(UserProfileDto profileDto, User user) {
        UserProfile profile = user.getUserProfile();

        String firstName = profileDto.firstName();
        if (firstName != null) {
            profile.setFirstName(firstName);
        }

        String lastName = profileDto.lastName();
        if (lastName != null) {
            profile.setLastName(lastName);
        }

        String description = profileDto.description();
        if (description != null) {
            profile.setDescription(description);
        }

        String picUrl = profileDto.pictureUrl();
        if (picUrl != null) {
            UserProfilePicture userProfilePicture = pictureService.createUserProfilePicture(picUrl, null);
            profile.setProfilePicture(userProfilePicture);
        }

        return mapToUserProfileResponse(user);
    }

    @Transactional
    public UserProfile createUserProfile(UserProfileDto profileDto, MultipartFile profilePicture, User user) {
        UserProfilePicture userProfilePicture = pictureService.createUserProfilePicture(profileDto.pictureUrl(), profilePicture);
        return UserMapper.toUserProfile(profileDto, user, userProfilePicture);
    }

    public User save(User user) {
        return repository.save(user);
    }

    private UserResponse mapToUserProfileResponse(User user) {
        Locale locale = LocaleContextHolder.getLocale();
        return UserMapper.toUserResponse(user, locale);
    }

}
