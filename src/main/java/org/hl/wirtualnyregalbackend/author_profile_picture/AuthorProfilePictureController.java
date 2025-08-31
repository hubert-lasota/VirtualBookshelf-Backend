package org.hl.wirtualnyregalbackend.author_profile_picture;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.author_profile_picture.entity.AuthorProfilePictureBinary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/author-profile-pictures")
@AllArgsConstructor
class AuthorProfilePictureController {

    private final AuthorProfilePictureService pictureService;

    @GetMapping(value = "/{pictureId}", produces = "image/*")
    public ResponseEntity<byte[]> findAuthorProfilePictureBinaryDataById(@PathVariable Long pictureId) {
        AuthorProfilePictureBinary pictureBinary = pictureService.findAuthorProfilePictureBinaryByPictureId(pictureId);
        return ResponseEntity
            .ok()
            .header("Content-Type", pictureBinary.getMimeType())
            .body(pictureBinary.getBinaryData());
    }

}
