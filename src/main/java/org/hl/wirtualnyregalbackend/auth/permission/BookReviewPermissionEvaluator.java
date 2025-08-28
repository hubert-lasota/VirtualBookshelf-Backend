package org.hl.wirtualnyregalbackend.auth.permission;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book_review.BookReviewService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class BookReviewPermissionEvaluator implements ResourcePermissionEvaluator {

    private final BookReviewService bookReviewService;


    @Override
    public boolean hasPermission(User user, Object targetId, ActionType actionType) {
        Long reviewId = (Long) targetId;
        return bookReviewService.isAuthor(reviewId, user);
    }

}
