package org.hl.wirtualnyregalbackend.application.book;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.application.book.exception.InvalidBookRatingException;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;

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
    private Byte rating;

    @Column(name = "rating_justification")
    private String ratingJustification;

    protected BookRating() { }

    public BookRating(byte rating, String ratingJustification, User user, Book book) {
        if (rating < 0 || rating > 5) {
            throw new InvalidBookRatingException("Rating must be between 0 and 5. Got: " + rating);
        }
        this.user = user;
        this.book = book;
        this.rating = rating;
        this.ratingJustification = ratingJustification;
    }

    public String getRatingJustification() {
        return ratingJustification;
    }

    public Byte getRating() {
        return rating;
    }

    public User getUser() {
        return user;
    }

}
