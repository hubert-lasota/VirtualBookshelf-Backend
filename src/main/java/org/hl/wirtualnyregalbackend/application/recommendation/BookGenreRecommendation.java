package org.hl.wirtualnyregalbackend.application.recommendation;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.application.book.BookGenre;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;

import java.util.Objects;

@Entity
@Table(name = "book_genre_recommendation")
public class BookGenreRecommendation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_genre_id")
    private BookGenre bookGenre;

    @Column(name = "recommendation_reason")
    private String reason;

    protected BookGenreRecommendation() { }

    public BookGenreRecommendation(User user, BookGenre bookGenre, String reason) {
        this.user = Objects.requireNonNull(user, "user cannot be null");
        this.bookGenre = Objects.requireNonNull(bookGenre, "bookGenre cannot be null");
        this.reason = reason;
    }


}
