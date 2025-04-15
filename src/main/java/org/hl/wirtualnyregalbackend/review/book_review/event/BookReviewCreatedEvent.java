package org.hl.wirtualnyregalbackend.review.book_review.event;

import org.hl.wirtualnyregalbackend.review.book_review.model.BookReview;

public record BookReviewCreatedEvent(BookReview bookReview) {
}
