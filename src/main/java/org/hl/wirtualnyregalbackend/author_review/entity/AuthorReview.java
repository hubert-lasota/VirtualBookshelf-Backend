package org.hl.wirtualnyregalbackend.author_review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.common.review.Review;
import org.hl.wirtualnyregalbackend.auth.entity.User;

import java.util.Objects;

@Entity
@Table(name = "author_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorReview extends Review {

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private Author author;

    public AuthorReview(Float rating, String content, User user, Author author) {
        super(rating, content, user);
        this.author = Objects.requireNonNull(author, "author cannot be null");
    }

}
