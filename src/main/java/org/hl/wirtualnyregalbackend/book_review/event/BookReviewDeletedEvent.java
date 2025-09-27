package org.hl.wirtualnyregalbackend.book_review.event;

import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;

public record BookReviewDeletedEvent(Long bookId) {

    public static BookReviewDeletedEvent from(BookReview bookReview) {
        return new BookReviewDeletedEvent(bookReview.getBook().getId());
    }

}
