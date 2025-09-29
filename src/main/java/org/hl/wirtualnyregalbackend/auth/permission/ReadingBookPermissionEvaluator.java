package org.hl.wirtualnyregalbackend.auth.permission;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookQueryService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ReadingBookPermissionEvaluator implements ResourcePermissionEvaluator {

    private final ReadingBookQueryService query;

    @Override
    public boolean hasPermission(User user, Object targetId, ActionType actionType) {
        Long readingBookId = (Long) targetId;
        return query.isReadingBookAuthor(readingBookId, user);
    }

}
