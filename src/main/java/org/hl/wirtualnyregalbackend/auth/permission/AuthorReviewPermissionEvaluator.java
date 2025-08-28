package org.hl.wirtualnyregalbackend.auth.permission;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author_review.AuthorReviewService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthorReviewPermissionEvaluator implements ResourcePermissionEvaluator {

    private final AuthorReviewService reviewService;

    @Override
    public boolean hasPermission(User user, Object targetId, ActionType actionType) {
        Long reviewId = (Long) targetId;
        return reviewService.isAuthorReviewOwner(reviewId, user);
    }

}
