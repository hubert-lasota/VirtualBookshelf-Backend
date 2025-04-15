package org.hl.wirtualnyregalbackend.author.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;


@Entity
@Table(name = "author_photo")
public class AuthorPhoto extends BaseEntity {

    @Column
    private String photoUrl;

    @OneToOne
    @JoinColumn(name = "photo_img_id")
    private AuthorPhotoImg photoImg;

    protected AuthorPhoto() { }

    public AuthorPhoto(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public AuthorPhoto(String photoUrl, AuthorPhotoImg photoImg) {
        this.photoUrl = photoUrl;
        this.photoImg = photoImg;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public AuthorPhotoImg getPhotoImg() {
        return photoImg;
    }

}
