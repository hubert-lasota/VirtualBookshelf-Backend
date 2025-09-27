package org.hl.wirtualnyregalbackend.author_review.event;

import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;

public record AuthorReviewCreatedOrUpdatedEvent(Long authorId) {

    public static AuthorReviewCreatedOrUpdatedEvent from(AuthorReview authorReview) {
        return new AuthorReviewCreatedOrUpdatedEvent(authorReview.getId());
    }
}
