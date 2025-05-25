package org.hl.wirtualnyregalbackend.author.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;


@Entity
@Table(name = "author_profile_picture")
public class AuthorProfilePicture extends BaseEntity {

    @Column
    private String url;

    @OneToOne
    @JoinColumn(name = "author_profile_picture_binary_id")
    private AuthorProfilePictureBinary authorProfilePictureBinary;

    protected AuthorProfilePicture() {
    }

    public AuthorProfilePicture(String url) {
        this.url = url;
    }

    public AuthorProfilePicture(String url, AuthorProfilePictureBinary authorProfilePictureBinary) {
        this.url = url;
        this.authorProfilePictureBinary = authorProfilePictureBinary;
    }

    public String getUrl() {
        return url;
    }

    public AuthorProfilePictureBinary getAuthorProfilePictureBinary() {
        return authorProfilePictureBinary;
    }

}
