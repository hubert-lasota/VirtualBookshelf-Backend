package org.hl.wirtualnyregalbackend.security.permission_checker;

import org.hl.wirtualnyregalbackend.common.ActionType;
import org.hl.wirtualnyregalbackend.security.exception.PermissionDeniedException;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.lang.Nullable;

public interface PermissionChecker {

    void hasPermission(@Nullable Object resourceId, ActionType actionType, User user) throws PermissionDeniedException;

}
