package org.hl.wirtualnyregalbackend.application.user;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;

@Entity
@Table(name = "user_profile_picture")
public class UserProfilePicture extends UpdatableBaseEntity {

    @OneToOne(optional = false)
    private User user;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private UserProfilePictureImg profilePictureImg;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    protected UserProfilePicture() { }

    public UserProfilePicture(User user, UserProfilePictureImg profilePictureImg, String profilePictureUrl) {
        this.user = user;
        this.profilePictureImg = profilePictureImg;
        this.profilePictureUrl = profilePictureUrl;
    }


    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

}
