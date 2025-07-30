package org.hl.wirtualnyregalbackend.book_review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.common.review.Review;
import org.hl.wirtualnyregalbackend.auth.entity.User;

import java.util.Objects;

@Entity
@Table(name = "book_review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookReview extends Review {

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    public BookReview(Float rating, String content, User user, Book book) {
        super(rating, content, user);
        this.book = Objects.requireNonNull(book, "book cannot be null.");
    }

}
