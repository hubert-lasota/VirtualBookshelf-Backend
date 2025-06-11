package org.hl.wirtualnyregalbackend.security.entity;


import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity
@Table(name = "authority")
public class Authority extends BaseEntity implements GrantedAuthority {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    protected Authority() {
    }

    public Authority(AuthorityName name) {
        this.name = name;
    }

    public void addUser(User user) {
        Objects.requireNonNull(user, "User cannot be null.");
        if (this.user == null) {
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
        return name.toString();
    }

    public AuthorityName getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Authority{" +
            "id=" + id +
            ", user=" + user +
            ", authority='" + name + '\'' +
            '}';
    }

}
