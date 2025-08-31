package org.hl.wirtualnyregalbackend.user_profile_picture.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BinaryBaseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Table(name = "user_profile_picture_binary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfilePictureBinary extends BinaryBaseEntity {

    public UserProfilePictureBinary(MultipartFile profilePicture) throws IOException {
        super(profilePicture);
    }

}
