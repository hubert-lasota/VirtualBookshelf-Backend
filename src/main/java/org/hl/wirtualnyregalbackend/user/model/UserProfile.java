package org.hl.wirtualnyregalbackend.user.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.genre.model.Genre;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user_profile")
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


    protected UserProfile() {
    }

    public UserProfile(String firstName, String lastName, String description, Set<Genre> genrePreferences, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.user = user;
        this.genrePreferences = genrePreferences;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public UserProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public Set<Genre> getGenrePreferences() {
        return Collections.unmodifiableSet(genrePreferences);
    }

}
