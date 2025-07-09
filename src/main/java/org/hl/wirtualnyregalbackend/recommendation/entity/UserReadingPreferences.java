package org.hl.wirtualnyregalbackend.recommendation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.security.entity.User;

import java.util.List;

@Entity
@Table(name = "user_reading_preferences")
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserReadingPreferences extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "readingPreferences", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGenrePreferences> genrePreferences;


}
