package org.hl.wirtualnyregalbackend.auth.permission;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfQueryService;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
class BookshelfPermissionEvaluator implements ResourcePermissionEvaluator {

    private final BookshelfQueryService query;

    @Override
    public boolean hasPermission(User user, Object targetId, ActionType actionType) {
        Long bookshelfId = (Long) targetId;
        return query.isUserBookshelfAuthor(bookshelfId, user.getId());
    }

}
