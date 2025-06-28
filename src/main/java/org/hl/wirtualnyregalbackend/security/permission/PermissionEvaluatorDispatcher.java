package org.hl.wirtualnyregalbackend.security.permission;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class PermissionEvaluatorDispatcher implements PermissionEvaluator {

    private final Map<ResourceType, ResourcePermissionEvaluator> evaluators = new HashMap<>();

    PermissionEvaluatorDispatcher(
        BookReviewPermissionEvaluator bookReviewPermissionEvaluator,
        BookshelfPermissionEvaluator bookshelfPermissionEvaluator,
        BookshelfBookPermissionEvaluator bookshelfBookPermissionEvaluator
    ) {
        evaluators.put(ResourceType.BOOK_REVIEW, bookReviewPermissionEvaluator);
        evaluators.put(ResourceType.BOOKSHELF, bookshelfPermissionEvaluator);
        evaluators.put(ResourceType.BOOKSHELF_BOOK, bookshelfBookPermissionEvaluator);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        throw new UnsupportedOperationException("This method is not supported. You should use hasPermission with targetId and targetType.");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        ResourceType resourceType = ResourceType.fromString(targetType);
        ResourcePermissionEvaluator evaluator = evaluators.get(resourceType);
        String permissionStr = (String) permission;
        ActionType actionType = ActionType.fromString(permissionStr);
        log.debug("Permission evaluator for resource type: {}, action type: {}, using evaluator: {}", resourceType, actionType, evaluator.getClass().getSimpleName());
        return evaluator.hasPermission(authentication, targetId, actionType);
    }

}
