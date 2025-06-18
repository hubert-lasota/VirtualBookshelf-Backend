package org.hl.wirtualnyregalbackend.author.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BinaryBaseEntity;

@Entity
@Table(name = "author_profile_picture_binary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorProfilePictureBinary extends BinaryBaseEntity {

    public AuthorProfilePictureBinary(byte[] binaryData, String mimeType, String fileName) {
        super(binaryData, mimeType, fileName);
    }

}
