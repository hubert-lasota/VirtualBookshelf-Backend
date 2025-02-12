package org.hl.wirtualnyregalbackend.security;

import org.hl.wirtualnyregalbackend.common.ResourceType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class PermissionCheckerFactory {

    private final Map<ResourceType, PermissionChecker> permissionCheckers = new EnumMap<>(ResourceType.class);

    public PermissionCheckerFactory(List<PermissionChecker> permissionCheckers) {
        for(PermissionChecker checker : permissionCheckers) {
            if(checker instanceof BookPermissionChecker) {
                this.permissionCheckers.put(ResourceType.BOOK, checker);
            } else if (checker instanceof BookshelfPermissionChecker) {
                this.permissionCheckers.put(ResourceType.BOOKSHELF, checker);
            } else if (checker instanceof BookRatingPermissionChecker) {
                this.permissionCheckers.put(ResourceType.BOOK_RATING, checker);
            } else {
                throw new IllegalArgumentException("Unsupported permission checker type: " + checker.getClass().getName());
            }
        }
    }

    public PermissionChecker getPermissionChecker(ResourceType resourceType) {
        return permissionCheckers.get(resourceType);
    }

}
