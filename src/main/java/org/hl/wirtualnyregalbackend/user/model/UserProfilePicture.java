package org.hl.wirtualnyregalbackend.user.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

import java.util.Objects;

@Entity
@Table(name = "user_profile_picture")
public class UserProfilePicture extends BaseEntity {

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_picture_img_id")
    private UserProfilePictureImg profilePictureImg;

    protected UserProfilePicture() { }


    public UserProfilePicture(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public UserProfilePicture(String profilePictureUrl, UserProfilePictureImg profilePictureImg) {
        this.profilePictureUrl = profilePictureUrl;
        this.profilePictureImg = Objects.requireNonNull(profilePictureImg, "profilePictureImg cannot be null.");
    }

    public void updateProfilePictureUrl(String profilePictureUrl) {
        if(profilePictureUrl != null) {
            this.profilePictureUrl = profilePictureUrl;
        }
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

}
