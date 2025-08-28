package org.hl.wirtualnyregalbackend.auth.permission;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ReadingNotePermissionEvaluator implements ResourcePermissionEvaluator {

    @Override
    public boolean hasPermission(User user, Object targetId, ActionType actionType) {
        return true;
    }

}
