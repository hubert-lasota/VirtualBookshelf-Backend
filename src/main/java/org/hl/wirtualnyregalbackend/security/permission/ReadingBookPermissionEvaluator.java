package org.hl.wirtualnyregalbackend.security.permission;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
class ReadingBookPermissionEvaluator implements ResourcePermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetId, ActionType actionType) {
        return true;
    }

}
