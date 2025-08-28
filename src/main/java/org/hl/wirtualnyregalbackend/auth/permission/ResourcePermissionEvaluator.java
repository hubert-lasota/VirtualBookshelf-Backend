package org.hl.wirtualnyregalbackend.auth.permission;

import org.hl.wirtualnyregalbackend.auth.entity.User;

interface ResourcePermissionEvaluator {

    boolean hasPermission(User user, Object targetId, ActionType actionType);

}
