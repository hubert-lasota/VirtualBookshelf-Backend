package org.hl.wirtualnyregalbackend.security.permission;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class PermissionEvaluatorDispatcher implements PermissionEvaluator {

    private final Map<ResourceType, ResourcePermissionEvaluator> evaluators = new HashMap<>();

    public PermissionEvaluatorDispatcher(BookReviewPermissionEvaluator bookReviewPermissionEvaluator,
                                         BookshelfPermissionEvaluator bookshelfPermissionEvaluator) {
        evaluators.put(ResourceType.BOOK_REVIEW, bookReviewPermissionEvaluator);
        evaluators.put(ResourceType.BOOKSHELF, bookshelfPermissionEvaluator);

    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        throw new UnsupportedOperationException("This method is not supported. You should use hasPermission with targetId and targetType.");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        ResourceType resourceType = ResourceType.valueOf(targetType.toUpperCase());
        ResourcePermissionEvaluator evaluator = evaluators.get(resourceType);
        ActionType actionType = ActionType.valueOf(((String) permission).toUpperCase());
        return evaluator.hasPermission(authentication, targetId, actionType);
    }

}
