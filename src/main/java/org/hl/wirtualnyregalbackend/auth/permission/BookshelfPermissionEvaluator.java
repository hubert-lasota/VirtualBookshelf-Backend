package org.hl.wirtualnyregalbackend.auth.permission;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
class BookshelfPermissionEvaluator implements ResourcePermissionEvaluator {

    private final BookshelfService bookshelfService;

    public BookshelfPermissionEvaluator(BookshelfService bookshelfService) {
        this.bookshelfService = bookshelfService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetId, ActionType actionType) {
        if (PermissionUtils.isAdmin(authentication)) {
            return true;
        }
        Long bookshelfId = (Long) targetId;
        User user = (User) authentication.getPrincipal();
        return bookshelfService.isUserBookshelfAuthor(bookshelfId, user.getId());
    }

}
