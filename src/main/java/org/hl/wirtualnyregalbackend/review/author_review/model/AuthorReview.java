package org.hl.wirtualnyregalbackend.review.author_review.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.review.model.Review;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.util.Objects;

@Entity
@Table(name = "author_review")
public class AuthorReview extends Review {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private Author author;

    protected AuthorReview() { }

    public AuthorReview(Float rating, String content, User user, Author author) {
        super(rating, content);
        this.author = Objects.requireNonNull(author, "author cannot be null");
        this.user = Objects.requireNonNull(user, "user cannot be null");
    }

}
