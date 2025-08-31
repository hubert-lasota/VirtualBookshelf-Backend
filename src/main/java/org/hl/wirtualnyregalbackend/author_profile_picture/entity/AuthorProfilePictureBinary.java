package org.hl.wirtualnyregalbackend.author_profile_picture.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BinaryBaseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Table(name = "author_profile_picture_binary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorProfilePictureBinary extends BinaryBaseEntity {

    public AuthorProfilePictureBinary(MultipartFile profilePicture) throws IOException {
        super(profilePicture);
    }

}
