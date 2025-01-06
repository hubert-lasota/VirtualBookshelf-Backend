package org.hl.wirtualnyregalbackend.infrastructure.security;

import org.hl.wirtualnyregalbackend.infrastructure.book.BookRepository;
import org.hl.wirtualnyregalbackend.infrastructure.security.exception.PermissionDeniedException;
import org.springframework.stereotype.Component;

@Component
public class BookPermissionChecker implements PermissionChecker {

    private final BookRepository bookRepository;

    public BookPermissionChecker(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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

//        Long bookshelfId = (Long) resourceId;
//        boolean isAuthor = bookshelfRepository.isUserBookshelfAuthor(bookshelfId, user.getId());
//        if(!isAuthor) {
//            throw new PermissionDeniedException(ResourceType.BOOKSHELF, actionType);
//        }
    }
}
