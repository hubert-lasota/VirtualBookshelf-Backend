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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "has_account")
    private Boolean hasAccount;

    protected Author() { }

    public Author(String fullName) {
        this(fullName, null, null, false);
    }

    public Author(String fullName, String externalApiId) {
        this(fullName, externalApiId, null, false);
    }

    public Author(String fullName, User user) {
        this(fullName, null, user, true);
    }

    private Author(String fullName, String externalApiId, User user, Boolean hasAccount) {
        this.fullName = baseValidateString(fullName, "fullName");
        this.externalApiId = externalApiId;
        this.user = user;
        this.hasAccount = hasAccount;
    }

    public String getFullName() {
        return fullName;
    }

}
