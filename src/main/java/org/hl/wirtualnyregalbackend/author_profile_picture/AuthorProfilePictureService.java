package org.hl.wirtualnyregalbackend.author_profile_picture;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.author_profile_picture.entity.AuthorProfilePicture;
import org.hl.wirtualnyregalbackend.author_profile_picture.entity.AuthorProfilePictureBinary;
import org.hl.wirtualnyregalbackend.author_profile_picture.exception.AuthorProfilePictureNotFoundException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class AuthorProfilePictureService {

    private final AuthorProfilePictureRepository pictureRepository;
    private final AuthorProfilePictureUrlBuilder urlBuilder;

    @Nullable
    @Transactional
    public AuthorProfilePicture createAuthorProfilePicture(@Nullable String authorPictureUrl, @Nullable MultipartFile authorPicture) {
        if (authorPicture == null && authorPictureUrl == null) {
            return null;
        }

        if (authorPictureUrl != null) {
            return new AuthorProfilePicture(authorPictureUrl);
        }

        try {
            AuthorProfilePictureBinary binary = new AuthorProfilePictureBinary(authorPicture);
            AuthorProfilePicture picture = new AuthorProfilePicture(null, binary);
            pictureRepository.saveAndFlush(picture);
            picture.setUrl(urlBuilder.buildPictureUrl(picture));
            return pictureRepository.save(picture);
        } catch (IOException e) {
            log.error("Error while reading author picture file.", e);
            return null;
        }
    }

    public AuthorProfilePictureBinary findAuthorProfilePictureBinaryByPictureId(Long authorProfilePictureId) {
        return pictureRepository
            .findById(authorProfilePictureId)
            .orElseThrow(() -> {
                log.warn("AuthorProfilePictureBinary not found with ID: {}", authorProfilePictureId);
                return new AuthorProfilePictureNotFoundException(authorProfilePictureId);
            })
            .getAuthorProfilePictureBinary();
    }

}
