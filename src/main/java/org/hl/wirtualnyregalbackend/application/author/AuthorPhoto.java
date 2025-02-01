package org.hl.wirtualnyregalbackend.application.author;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;

import java.util.Objects;

import static org.hl.wirtualnyregalbackend.application.common.ValidationUtils.baseValidateString;

@Entity
@Table(name = "author_photo")
public class AuthorPhoto extends UpdatableBaseEntity {

    @Column
    private String photoUrl;

    @OneToOne
    @JoinColumn(name = "photo_img_id")
    private AuthorPhotoImg photoImg;

    protected AuthorPhoto() { }

    public AuthorPhoto(String photoUrl) {
        this.photoUrl = baseValidateString(photoUrl, "photoUrl");
    }

    public AuthorPhoto(String photoUrl, AuthorPhotoImg photoImg) {
        this.photoUrl = baseValidateString(photoUrl, "photoUrl");
        this.photoImg = Objects.requireNonNull(photoImg, "photoImg cannot be null");
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public AuthorPhotoImg getPhotoImg() {
        return photoImg;
    }

}
