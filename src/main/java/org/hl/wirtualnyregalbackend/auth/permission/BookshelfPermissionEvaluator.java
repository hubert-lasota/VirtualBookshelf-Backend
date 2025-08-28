package org.hl.wirtualnyregalbackend.auth.permission;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfService;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
class BookshelfPermissionEvaluator implements ResourcePermissionEvaluator {

    private final BookshelfService bookshelfService;

    @Override
    public boolean hasPermission(User user, Object targetId, ActionType actionType) {
        Long bookshelfId = (Long) targetId;
        return bookshelfService.isUserBookshelfAuthor(bookshelfId, user.getId());
    }

}
