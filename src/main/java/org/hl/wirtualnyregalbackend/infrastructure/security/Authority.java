package org.hl.wirtualnyregalbackend.infrastructure.security;


import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
