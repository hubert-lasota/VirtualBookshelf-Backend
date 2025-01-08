package org.hl.wirtualnyregalbackend.infrastructure.security;


import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity
@Table(name = "authority")
public class Authority extends BaseEntity implements GrantedAuthority {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private AuthorityType authority;

    protected Authority() { }

    public Authority(String authority) {
        this.authority = AuthorityType.valueOf(authority.toUpperCase());
    }

    public void addUser(User user) {
        Objects.requireNonNull(user, "User cannot be null.");
        if(this.user == null) {
            this.user = user;
        } else {
            throw new RuntimeException("User has already been added.");
        }
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getAuthority() {
        return authority.toString();
    }

    public AuthorityType getAuthorityType() {
        return authority;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", user=" + user +
                ", authority='" + authority + '\'' +
                '}';
    }

}
