package org.hl.wirtualnyregalbackend.author.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.common.jpa.BinaryBaseEntity;

@Entity
@Table(name = "author_photo_img")
public class AuthorProfilePictureBinary extends BinaryBaseEntity {

    protected AuthorProfilePictureBinary() {

    }

    public AuthorProfilePictureBinary(byte[] binaryData, String mimeType, String fileName) {
        super(binaryData, mimeType, fileName);
    }

}
