package org.hl.wirtualnyregalbackend.author.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.common.jpa.BinaryBaseEntity;

@Entity
@Table(name = "author_profile_picture_binary")
public class AuthorProfilePictureBinary extends BinaryBaseEntity {

    protected AuthorProfilePictureBinary() {

    }

    public AuthorProfilePictureBinary(byte[] binaryData, String mimeType, String fileName) {
        super(binaryData, mimeType, fileName);
    }

}
