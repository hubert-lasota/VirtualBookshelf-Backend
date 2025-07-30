package org.hl.wirtualnyregalbackend.auth.permission;

public enum ResourceType {
    BOOK,
    BOOK_REVIEW,
    BOOKSHELF,
    READING_BOOK;

    public static ResourceType fromString(String resourceType) {
        return ResourceType.valueOf(resourceType.toUpperCase());
    }

}
