package org.hl.wirtualnyregalbackend.security.permission;

public enum ActionType {
    CREATE, READ, UPDATE, DELETE;

    public static ActionType fromString(String actionType) {
        return ActionType.valueOf(actionType.toUpperCase());
    }

}
