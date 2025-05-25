package org.hl.wirtualnyregalbackend.security.permission;

import org.springframework.security.core.Authentication;

interface ResourcePermissionEvaluator {

    boolean hasPermission(Authentication authentication, Object targetId, ActionType actionType);

}
