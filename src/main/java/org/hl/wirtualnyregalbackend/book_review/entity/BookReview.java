package org.hl.wirtualnyregalbackend.book_review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.common.review.Review;
import org.hl.wirtualnyregalbackend.security.entity.User;

import java.util.Objects;

@Entity
@Table(name = "book_review")
@Getter
public class BookReview extends Review {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    protected BookReview() {
    }

    public BookReview(Float rating, String content, User user, Book book) {
        super(rating, content);
        this.user = Objects.requireNonNull(user, "user cannot be null.");
        this.book = Objects.requireNonNull(book, "book cannot be null.");
    }

}
