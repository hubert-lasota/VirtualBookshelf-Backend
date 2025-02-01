package org.hl.wirtualnyregalbackend.infrastructure.security.exception;

import org.hl.wirtualnyregalbackend.application.common.ActionType;
import org.hl.wirtualnyregalbackend.infrastructure.common.ResourceType;

public class PermissionDeniedException extends RuntimeException {

    private final ResourceType resourceType;
    private final ActionType actionType;

    public PermissionDeniedException(ResourceType resourceType, ActionType actionType) {
        this.resourceType = resourceType;
        this.actionType = actionType;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public ActionType getActionType() {
        return actionType;
    }

}
