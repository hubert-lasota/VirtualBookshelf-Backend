package org.hl.wirtualnyregalbackend.author.entity;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.security.entity.User;

@Entity
@Table(name = "author")
public class Author extends BaseEntity {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "description")
    private String description;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "author_profile_picture_id")
    private AuthorProfilePicture AuthorProfilePicture;

    protected Author() {
    }

    public Author(String fullName, String description, AuthorProfilePicture authorProfilePicture, User user) {
        this.fullName = fullName;
        this.AuthorProfilePicture = authorProfilePicture;
        this.user = user;
        this.description = description;
    }


    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public AuthorProfilePicture getAuthorProfilePicture() {
        return AuthorProfilePicture;
    }
}
