package org.hl.wirtualnyregalbackend.application.book;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.application.book.exception.InvalidBookRatingException;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book_rating")
public class BookRating extends UpdatableBaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "rating_justification")
    private String ratingJustification;

    @Transient
    private final List<Float> validRatings = List.of(1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f, 4.5f, 5f);

    protected BookRating() { }

    public BookRating(Float rating, String ratingJustification, User user, Book book) {
        this.rating = validateRating(rating);
        this.user = Objects.requireNonNull(user, "user cannot be null.");
        this.book = Objects.requireNonNull(book, "book cannot be null.");
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

    public String getRatingJustification() {
        return ratingJustification;
    }

    public Float getRating() {
        return rating;
    }

    public User getUser() {
        return user;
    }

}
