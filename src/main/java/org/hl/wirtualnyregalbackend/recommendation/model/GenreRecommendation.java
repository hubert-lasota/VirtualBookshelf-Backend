package org.hl.wirtualnyregalbackend.recommendation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.genre.model.Genre;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.util.Objects;

@Entity
@Table(name = "genre_recommendation")
public class GenreRecommendation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_genre_id")
    private Genre genre;

    protected GenreRecommendation() {
    }

    public GenreRecommendation(User user, Genre genre) {
        this.user = Objects.requireNonNull(user, "user cannot be null");
        this.genre = Objects.requireNonNull(genre, "bookGenre cannot be null");
    }


}
