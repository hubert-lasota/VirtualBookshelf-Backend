package org.hl.wirtualnyregalbackend.user.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.UpdatableBaseEntity;

import java.util.Objects;

import static org.hl.wirtualnyregalbackend.common.util.ValidationUtils.baseValidateString;

@Entity
@Table(name = "user_profile_picture")
public class UserProfilePicture extends UpdatableBaseEntity {

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_picture_img_id")
    private UserProfilePictureImg profilePictureImg;

    protected UserProfilePicture() { }


    public UserProfilePicture(String profilePictureUrl) {
        this.profilePictureUrl = baseValidateString(profilePictureUrl, "profilePictureUrl");
    }

    public UserProfilePicture(String profilePictureUrl, UserProfilePictureImg profilePictureImg) {
        this.profilePictureUrl = baseValidateString(profilePictureUrl, "profilePictureUrl");
        this.profilePictureImg = Objects.requireNonNull(profilePictureImg, "profilePictureImg cannot be null.");
    }

    public void updateProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = baseValidateString(profilePictureUrl, "profilePictureUrl");
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

}
