package org.hl.wirtualnyregalbackend.auth.permission;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_note.ReadingNoteQueryService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ReadingNotePermissionEvaluator implements ResourcePermissionEvaluator {

    private final ReadingNoteQueryService query;

    @Override
    public boolean hasPermission(User user, Object targetId, ActionType actionType) {
        Long noteId = (Long) targetId;
        return query.isNoteAuthor(noteId, user);
    }

}
