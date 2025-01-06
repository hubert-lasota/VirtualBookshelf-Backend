package org.hl.wirtualnyregalbackend.infrastructure.security;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.application.user.UserProfilePicture;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends UpdatableBaseEntity implements UserDetails {

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne
    private UserProfilePicture profilePicture;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Authority> authorities = new ArrayList<>();


    protected User() { }

    public User(String username, String password, Authority... authorities) {
        this.username = username;
        this.password = password;
        this.authorities.addAll(List.of(authorities));
    }

    public void setProfilePicture(UserProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public UserProfilePicture getProfilePicture() {
        return profilePicture;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id + '}';
    }

}
