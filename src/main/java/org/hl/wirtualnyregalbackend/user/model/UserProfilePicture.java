package org.hl.wirtualnyregalbackend.user.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

import java.util.Objects;

@Entity
@Table(name = "user_profile_picture")
public class UserProfilePicture extends BaseEntity {

    @Column
    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_picture_binary_id")
    private UserProfilePictureBinary binaryPicture;

    protected UserProfilePicture() {
    }

    public UserProfilePicture(String url, UserProfilePictureBinary binaryPicture) {
        this.url = url;
        this.binaryPicture = Objects.requireNonNull(binaryPicture, "binaryPicture cannot be null.");
    }

    public void setUrlIfNotNull(String url) {
        if (url != null) {
            this.url = url;
        }
    }

    public String getUrl() {
        return url;
    }

}
