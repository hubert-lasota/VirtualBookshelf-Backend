package org.hl.wirtualnyregalbackend.auth.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authority")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Authority extends BaseEntity implements GrantedAuthority {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    @Override
    public String getAuthority() {
        return name.toString();
    }

}
