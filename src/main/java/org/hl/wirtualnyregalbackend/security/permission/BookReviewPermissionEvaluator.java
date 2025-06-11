package org.hl.wirtualnyregalbackend.security.permission;

import org.hl.wirtualnyregalbackend.book_review.BookReviewService;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
class BookReviewPermissionEvaluator implements ResourcePermissionEvaluator {

    private final BookReviewService bookReviewService;

    public BookReviewPermissionEvaluator(BookReviewService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetId, ActionType actionType) {
        return false;
    }

    public boolean isAuthor(Long bookRatingId, User user) {
        return bookReviewService.isAuthor(bookRatingId, user.getId());
    }

}
