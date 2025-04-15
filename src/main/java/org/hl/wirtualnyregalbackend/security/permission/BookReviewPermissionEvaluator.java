package org.hl.wirtualnyregalbackend.security.permission;

import org.hl.wirtualnyregalbackend.review.book_review.dao.BookReviewRepository;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class BookReviewPermissionEvaluator implements ResourcePermissionEvaluator {

    private final BookReviewRepository bookReviewRepository;

    public BookReviewPermissionEvaluator(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetId, ActionType actionType) {
        return false;
    }

    public boolean isAuthor(Long bookRatingId, User user) {
        return bookReviewRepository.isAuthor(bookRatingId, user.getId());
    }

}
