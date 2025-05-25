package org.hl.wirtualnyregalbackend.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.jpa.BinaryBaseEntity;

@Entity
@Table(name = "user_profile_picture_binary")
public class UserProfilePictureBinary extends BinaryBaseEntity {

    protected UserProfilePictureBinary() {
    }

    public UserProfilePictureBinary(byte[] binaryData, String mimeType, String fileName) {
        super(binaryData, mimeType, fileName);
    }

}
