package org.hl.wirtualnyregalbackend.book_review.event;

import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;

public record BookReviewCreatedOrUpdatedEvent(Long bookId, Float rating) {

    public static BookReviewCreatedOrUpdatedEvent from(BookReview bookReview) {
        return new BookReviewCreatedOrUpdatedEvent(bookReview.getBook().getId(), bookReview.getRating());
    }

}
