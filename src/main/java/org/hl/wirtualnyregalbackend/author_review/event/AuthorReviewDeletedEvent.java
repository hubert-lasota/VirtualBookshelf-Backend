package org.hl.wirtualnyregalbackend.author_review.event;

import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;

public record AuthorReviewDeletedEvent(Long authorId) {

    public static AuthorReviewDeletedEvent from(AuthorReview authorReview) {
        return new AuthorReviewDeletedEvent(authorReview.getId());
    }

}
