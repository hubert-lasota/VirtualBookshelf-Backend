package org.hl.wirtualnyregalbackend.infrastructure.security;

import org.hl.wirtualnyregalbackend.application.common.ActionType;
import org.hl.wirtualnyregalbackend.infrastructure.security.exception.PermissionDeniedException;
import org.springframework.lang.Nullable;

public interface PermissionChecker {

    void hasPermission(@Nullable Object resourceId, ActionType actionType, User user) throws PermissionDeniedException;

}
