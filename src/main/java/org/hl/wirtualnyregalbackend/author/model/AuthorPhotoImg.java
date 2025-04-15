package org.hl.wirtualnyregalbackend.author.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "author_photo_img")
public class AuthorPhotoImg extends BaseEntity {

    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_type")
    private String imgType;

    protected AuthorPhotoImg() { }

    public AuthorPhotoImg(byte[] img, String imgType) {
        this.img = img;
        this.imgType = imgType;
    }

    public byte[] getImg() {
        return img;
    }

    public String getImgType() {
        return imgType;
    }

}
