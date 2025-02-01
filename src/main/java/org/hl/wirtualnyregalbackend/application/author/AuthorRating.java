package org.hl.wirtualnyregalbackend.application.author;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.application.book.exception.InvalidBookRatingException;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "author_rating")
public class AuthorRating extends UpdatableBaseEntity {

    @Column(name = "rating")
    private Float rating;

    @Column(name = "rating_justification")
    private String ratingJustification;


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private Author author;

    @Transient
    private final List<Float> validRatings = List.of(1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f, 4.5f, 5f);

    protected AuthorRating() { }

    public AuthorRating(Float rating, String ratingJustification, User user, Author author) {
        this.rating = validateRating(rating);
        this.author = Objects.requireNonNull(author, "author cannot be null");
        this.user = Objects.requireNonNull(user, "user cannot be null");
        this.ratingJustification = ratingJustification;
    }


    public void updateRating(Float rating) {
        this.rating = validateRating(rating);
    }

    public void updateRatingJustification(String ratingJustification) {
        this.ratingJustification = ratingJustification;
    }

    private Float validateRating(Float rating) {
        Objects.requireNonNull(rating, "rating cannot be null.");
        if (validRatings.contains(rating)) {
            return rating;
        } else {
            throw new InvalidBookRatingException("Invalid rating value: %s Valid rating should be one of %s"
                    .formatted(rating, validRatings));
        }
    }
}
