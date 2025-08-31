package org.hl.wirtualnyregalbackend.user_profile_picture;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.user_profile_picture.entity.UserProfilePicture;
import org.hl.wirtualnyregalbackend.user_profile_picture.entity.UserProfilePictureBinary;
import org.hl.wirtualnyregalbackend.user_profile_picture.exception.UserProfilePictureNotFoundException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class UserProfilePictureService {

    private final UserProfilePictureRepository pictureRepository;
    private final UserProfilePictureUrlBuilder urlBuilder;

    @Nullable
    @Transactional
    public UserProfilePicture createUserProfilePicture(@Nullable String pictureUrl, @Nullable MultipartFile picture) {
        if (picture == null && pictureUrl == null) {
            return null;
        }

        if (pictureUrl != null) {
            return new UserProfilePicture(pictureUrl);
        }

        try {
            UserProfilePictureBinary binary = new UserProfilePictureBinary(picture);
            UserProfilePicture userPicture = new UserProfilePicture(null, binary);
            pictureRepository.saveAndFlush(userPicture);
            userPicture.setUrl(urlBuilder.buildProfilePictureUrl(userPicture));
            return pictureRepository.save(userPicture);
        } catch (Exception e) {
            log.error("Error while creating UserProfilePictureBinary", e);
            return null;
        }
    }

    public UserProfilePictureBinary findUserProfilePictureBinaryByPictureId(Long pictureId) {
        return pictureRepository
            .findById(pictureId)
            .orElseThrow(() -> {
                log.warn("UserProfilePictureBinary not found with ID: {}", pictureId);
                return new UserProfilePictureNotFoundException(pictureId);
            })
            .getBinaryPicture();
    }

}
