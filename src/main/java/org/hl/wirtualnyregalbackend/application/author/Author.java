package org.hl.wirtualnyregalbackend.application.author;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.application.book.Book;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();

    protected Author() { }

    public Author(String fullName) {
        this.fullName = fullName;
        this.hasAccount = false;
    }

    public Author(String fullName, String externalApiId) {
        this.fullName = fullName;
        this.externalApiId = externalApiId;
        this.hasAccount = false;
    }

    public Author(String fullName, User user) {
        this.fullName = fullName;
        this.user = user;
        this.hasAccount = true;
    }

    public String getFullName() {
        return fullName;
    }

}
