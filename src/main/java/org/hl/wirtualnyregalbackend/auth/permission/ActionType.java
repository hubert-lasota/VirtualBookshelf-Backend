package org.hl.wirtualnyregalbackend.auth.permission;

public enum ActionType {
    CREATE, READ, UPDATE, DELETE;

    public static ActionType fromString(String actionType) {
        return ActionType.valueOf(actionType.toUpperCase());
    }

}
