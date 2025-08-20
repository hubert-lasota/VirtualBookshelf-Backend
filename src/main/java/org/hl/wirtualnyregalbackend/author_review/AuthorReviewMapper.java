package org.hl.wirtualnyregalbackend.author_review;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author_review.dto.AuthorReviewCreateRequest;
import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;

class AuthorReviewMapper {

    private AuthorReviewMapper() {
    }

    public static AuthorReview toAuthorReview(AuthorReviewCreateRequest reviewDto, Author author, User user) {
        return new AuthorReview(reviewDto.getRating(), reviewDto.getContent(), user, author);
    }

}
