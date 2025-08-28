package org.hl.wirtualnyregalbackend.auth.permission;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.springframework.stereotype.Component;

@Component
class ReadingBookPermissionEvaluator implements ResourcePermissionEvaluator {

    @Override
    public boolean hasPermission(User user, Object targetId, ActionType actionType) {
        return true;
    }

}
