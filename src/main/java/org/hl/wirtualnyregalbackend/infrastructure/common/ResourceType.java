package org.hl.wirtualnyregalbackend.infrastructure.common;

public enum ResourceType {

    BOOK, BOOKSHELF, BOOK_RATING, AUTHOR, USER, GET_FROM_CLASS_NAME;

    public ResourceType resolveResourceTypeFromClass(Class<?> clazz) {
        if(this != GET_FROM_CLASS_NAME) {
            throw new IllegalStateException("Cannot use this method when state is different from %s"
                    .formatted(GET_FROM_CLASS_NAME.toString()));
        }
        String className = clazz.getSimpleName().toUpperCase();
        if(className.contains("BOOKSHELF")) {
            return ResourceType.BOOKSHELF;
        } else if(className.contains("BOOK_RATING")) {
            return ResourceType.BOOK_RATING;
        } else if(className.contains("BOOK")) {
            return ResourceType.BOOK;
        } else if(className.contains("AUTHOR")) {
            return ResourceType.AUTHOR;
        } else if(className.contains("USER")) {
            return ResourceType.USER;
        } else {
            throw new IllegalArgumentException("Could not resolve class name=%s".formatted(className));
        }
    }

}
