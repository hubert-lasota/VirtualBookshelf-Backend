package org.hl.wirtualnyregalbackend.user_profile_picture;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.user_profile_picture.entity.UserProfilePictureBinary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user-profile-pictures")
@AllArgsConstructor
class UserProfilePictureController {

    private final UserProfilePictureService pictureService;

    @GetMapping(value = "/{pictureId}", produces = "image/*")
    public ResponseEntity<byte[]> findUserProfilePictureBinaryDataById(@PathVariable Long pictureId) {
        UserProfilePictureBinary pictureBinary = pictureService.findUserProfilePictureBinaryByPictureId(pictureId);
        return ResponseEntity
            .ok()
            .header("Content-Type", pictureBinary.getMimeType())
            .body(pictureBinary.getBinaryData());
    }

}
