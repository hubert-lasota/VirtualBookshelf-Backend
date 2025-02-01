package org.hl.wirtualnyregalbackend.application.author;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;

import static org.hl.wirtualnyregalbackend.application.common.ValidationUtils.baseValidateString;

@Entity
@Table(name = "author")
public class Author extends UpdatableBaseEntity {

    @Column(name = "external_api_id")
    private String externalApiId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "description")
    private String description;

    @Column(name = "has_account")
    private Boolean hasAccount;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "photo_id")
    private AuthorPhoto photo;

    protected Author() { }

    public Author(String fullName) {
        this(fullName, null, null, null,  null,false);
    }

    public Author(String fullName, String externalApiId) {
        this(fullName, externalApiId, null, null, null, false);
    }

    public Author(String fullName, User user) {
        this(fullName, null, null, user, null, true);
    }

    private Author(String fullName, String externalApiId, AuthorPhoto authorPhoto, User user, String description, Boolean hasAccount) {
        this.fullName = baseValidateString(fullName, "fullName");
        this.externalApiId = externalApiId;
        this.photo = authorPhoto;
        this.user = user;
        this.description = description;
        this.hasAccount = hasAccount;
    }

    public String getFullName() {
        return fullName;
    }

    public String getExternalApiId() {
        return externalApiId;
    }

}
