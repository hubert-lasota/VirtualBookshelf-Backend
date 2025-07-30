package org.hl.wirtualnyregalbackend.auth.permission;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book_review.BookReviewService;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class BookReviewPermissionEvaluator implements ResourcePermissionEvaluator {

    private final BookReviewService bookReviewService;


    @Override
    public boolean hasPermission(Authentication authentication, Object targetId, ActionType actionType) {
        return false;
    }

    public boolean isAuthor(Long bookRatingId, User user) {
        return bookReviewService.isAuthor(bookRatingId, user.getId());
    }

}
