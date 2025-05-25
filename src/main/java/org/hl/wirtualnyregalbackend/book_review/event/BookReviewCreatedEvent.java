package org.hl.wirtualnyregalbackend.book_review.event;

import org.hl.wirtualnyregalbackend.book_review.model.BookReview;

public record BookReviewCreatedEvent(BookReview bookReview) {
}
