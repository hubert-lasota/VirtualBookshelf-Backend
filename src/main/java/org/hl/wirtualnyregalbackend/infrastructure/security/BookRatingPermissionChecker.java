package org.hl.wirtualnyregalbackend.infrastructure.security;

import org.hl.wirtualnyregalbackend.application.common.ActionType;
import org.hl.wirtualnyregalbackend.infrastructure.book.BookRatingRepository;
import org.hl.wirtualnyregalbackend.infrastructure.common.ResourceType;
import org.hl.wirtualnyregalbackend.infrastructure.security.exception.PermissionDeniedException;

public class BookRatingPermissionChecker implements PermissionChecker {

    private final BookRatingRepository bookRatingRepository;

    public BookRatingPermissionChecker(BookRatingRepository bookRatingRepository) {
        this.bookRatingRepository = bookRatingRepository;
    }

    @Override
    public void hasPermission(Object resourceId, ActionType actionType, User user) throws PermissionDeniedException {
        if(actionType.equals(ActionType.CREATE)) {
            return;
        }

        boolean isAdmin = PermissionUtils.hasUserAuthority(user, AuthorityType.ADMIN);
        if (isAdmin) {
            return;
        }

        Long bookRatingId = (Long) resourceId;
        boolean isAuthor = bookRatingRepository.isBookRatingAuthor(bookRatingId, user.getId());
        if(!isAuthor) {
            throw new PermissionDeniedException(ResourceType.BOOK_RATING, actionType);
        }
    }
}
