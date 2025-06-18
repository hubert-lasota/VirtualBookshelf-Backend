package org.hl.wirtualnyregalbackend.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BinaryBaseEntity;

@Entity
@Table(name = "user_profile_picture_binary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfilePictureBinary extends BinaryBaseEntity {

    public UserProfilePictureBinary(byte[] binaryData, String mimeType, String fileName) {
        super(binaryData, mimeType, fileName);
    }

}
