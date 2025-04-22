package org.hl.wirtualnyregalbackend.author.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.security.model.User;

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
    @JoinColumn(name = "photo_id")
    private AuthorPhoto photo;

    protected Author() {
    }

    public Author(String fullName, String description, AuthorPhoto authorPhoto, User user) {
        this.fullName = fullName;
        this.photo = authorPhoto;
        this.user = user;
        this.description = description;
    }


    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public AuthorPhoto getPhoto() {
        return photo;
    }
}
