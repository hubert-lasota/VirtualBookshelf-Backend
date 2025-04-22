package org.hl.wirtualnyregalbackend.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "user_profile_picture_img")
public class UserProfilePictureImg extends BaseEntity {

    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_type")
    private String imgType;

    protected UserProfilePictureImg() {
    }

    public UserProfilePictureImg(byte[] img, String imgType) {
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
