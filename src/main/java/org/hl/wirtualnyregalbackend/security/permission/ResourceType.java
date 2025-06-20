package org.hl.wirtualnyregalbackend.security.permission;

public enum ResourceType {
    BOOK,
    BOOK_REVIEW,
    BOOKSHELF;

    public static ResourceType fromString(String resourceType) {
        return ResourceType.valueOf(resourceType.toUpperCase());
    }

}
