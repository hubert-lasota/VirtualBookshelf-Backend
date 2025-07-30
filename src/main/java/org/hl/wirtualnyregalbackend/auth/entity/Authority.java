package org.hl.wirtualnyregalbackend.auth.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authority")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority extends BaseEntity implements GrantedAuthority {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    public Authority(AuthorityName name) {
        this.name = name;
    }


    @Override
    public String getAuthority() {
        return name.toString();
    }

    @Override
    public String toString() {
        return "Authority{" +
            "id=" + id +
            ", authority='" + name + '\'' +
            '}';
    }

}
