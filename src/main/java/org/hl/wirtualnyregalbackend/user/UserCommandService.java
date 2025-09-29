package org.hl.wirtualnyregalbackend.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.user.dto.UserProfileDto;
import org.hl.wirtualnyregalbackend.user.entity.UserProfile;
import org.hl.wirtualnyregalbackend.user_profile_picture.UserProfilePictureService;
import org.hl.wirtualnyregalbackend.user_profile_picture.entity.UserProfilePicture;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class UserCommandService {

    private final UserRepository repository;
    private final UserProfilePictureService pictureService;

    @Transactional
    public UserProfile createUserProfile(UserProfileDto profileDto, MultipartFile profilePicture, User user) {
        UserProfilePicture userProfilePicture = pictureService.createUserProfilePicture(profileDto.pictureUrl(), profilePicture);
        return UserMapper.toUserProfile(profileDto, user, userProfilePicture);
    }

    public User save(User user) {
        return repository.save(user);
    }
}
