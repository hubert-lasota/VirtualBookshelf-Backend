package org.hl.wirtualnyregalbackend.security.permission;

import org.hl.wirtualnyregalbackend.bookshelf.dao.BookshelfRepository;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class BookshelfPermissionEvaluator implements ResourcePermissionEvaluator {

    private BookshelfRepository bookshelfRepository;

    public BookshelfPermissionEvaluator(BookshelfRepository bookshelfRepository) {
        this.bookshelfRepository = bookshelfRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetId, ActionType actionType) {
        if(PermissionUtils.isAdmin(authentication)) {
            return true;
        }
        Long bookshelfId = (Long) targetId;
        User user = (User) authentication.getPrincipal();
        return bookshelfRepository.isUserBookshelfAuthor(bookshelfId, user.getId());
    }

}
