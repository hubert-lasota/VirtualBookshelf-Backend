package org.hl.wirtualnyregalbackend.auth.permission;

public enum ResourceType {
    BOOK_REVIEW,
    AUTHOR_REVIEW,
    BOOKSHELF,
    READING_BOOK,
    READING_NOTE,
    READING_SESSION,
    CHALLENGE;

    public static ResourceType fromString(String resourceType) {
        resourceType = resourceType.replace(" ", "_");
        return ResourceType.valueOf(resourceType.toUpperCase());
    }

}
