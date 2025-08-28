package org.hl.wirtualnyregalbackend.auth.permission;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfService;
import org.springframework.stereotype.Component;


@Component
class BookshelfPermissionEvaluator implements ResourcePermissionEvaluator {

    private final BookshelfService bookshelfService;

    public BookshelfPermissionEvaluator(BookshelfService bookshelfService) {
        this.bookshelfService = bookshelfService;
    }

    @Override
    public boolean hasPermission(User user, Object targetId, ActionType actionType) {
        Long bookshelfId = (Long) targetId;
        return bookshelfService.isUserBookshelfAuthor(bookshelfId, user.getId());
    }

}
