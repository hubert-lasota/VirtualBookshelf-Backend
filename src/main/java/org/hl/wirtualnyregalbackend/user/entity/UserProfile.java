package org.hl.wirtualnyregalbackend.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.security.entity.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user_profile")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserProfile extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_picture_id")
    private UserProfilePicture profilePicture;

    @ManyToMany
    @JoinTable(
        name = "user_genre_preferences",
        joinColumns = @JoinColumn(name = "user_profile_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genrePreferences = new HashSet<>();


    public Set<Genre> getGenrePreferences() {
        return Collections.unmodifiableSet(genrePreferences);
    }

}
