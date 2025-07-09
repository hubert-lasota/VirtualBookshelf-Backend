package org.hl.wirtualnyregalbackend.security.permission;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ReadingNotePermissionEvaluator implements ResourcePermissionEvaluator {


    @Override
    public boolean hasPermission(Authentication authentication, Object targetId, ActionType actionType) {
        return true;
    }

}
