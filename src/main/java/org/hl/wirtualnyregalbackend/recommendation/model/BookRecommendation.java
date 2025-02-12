package org.hl.wirtualnyregalbackend.recommendation.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.book.model.Book;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.security.model.User;

@Entity
@Table(name = "book_recommendation")
public class BookRecommendation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "recommendation_reason")
    private String reason;

    protected BookRecommendation() { }

    public BookRecommendation(User user, Book book, String reason) {
        this.user = user;
        this.book = book;
        this.reason = reason;
    }

}
