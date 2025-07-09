package org.hl.wirtualnyregalbackend.security.permission;

public enum ResourceType {
    BOOK,
    BOOK_REVIEW,
    BOOKSHELF,
    READING_BOOK;

    public static ResourceType fromString(String resourceType) {
        return ResourceType.valueOf(resourceType.toUpperCase());
    }

}
