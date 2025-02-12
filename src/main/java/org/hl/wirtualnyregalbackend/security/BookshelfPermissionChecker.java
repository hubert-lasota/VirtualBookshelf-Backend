package org.hl.wirtualnyregalbackend.security;

import org.hl.wirtualnyregalbackend.bookshelf.BookshelfRepository;
import org.hl.wirtualnyregalbackend.common.ActionType;
import org.hl.wirtualnyregalbackend.common.ResourceType;
import org.hl.wirtualnyregalbackend.security.exception.PermissionDeniedException;
import org.hl.wirtualnyregalbackend.security.model.AuthorityType;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.stereotype.Component;


@Component
public class BookshelfPermissionChecker implements PermissionChecker {

    private BookshelfRepository bookshelfRepository;

    public BookshelfPermissionChecker(BookshelfRepository bookshelfRepository) {
        this.bookshelfRepository = bookshelfRepository;
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

        Long bookshelfId = (Long) resourceId;
        boolean isAuthor = bookshelfRepository.isUserBookshelfAuthor(bookshelfId, user.getId());
        if(!isAuthor) {
            throw new PermissionDeniedException(ResourceType.BOOKSHELF, actionType);
        }
    }

}
