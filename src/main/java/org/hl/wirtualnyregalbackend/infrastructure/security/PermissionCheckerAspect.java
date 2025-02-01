package org.hl.wirtualnyregalbackend.infrastructure.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.hl.wirtualnyregalbackend.application.common.ActionType;
import org.hl.wirtualnyregalbackend.infrastructure.common.ResourceType;
import org.hl.wirtualnyregalbackend.infrastructure.security.annotation.RequiresPermission;
import org.hl.wirtualnyregalbackend.infrastructure.security.exception.BadPermissionAccessConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class PermissionCheckerAspect {

    private final PermissionCheckerFactory permissionCheckerFactory;

    public PermissionCheckerAspect(PermissionCheckerFactory permissionCheckerFactory) {
        this.permissionCheckerFactory = permissionCheckerFactory;
    }


    @Before("@annotation(requiresPermission)")
    public void checkPermission(JoinPoint joinPoint, RequiresPermission requiresPermission) {
        Object resourceId = getResourceId(joinPoint, requiresPermission);
        ResourceType resourceType = getResourceType(joinPoint, requiresPermission);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        ActionType actionType = getActionType(joinPoint, requiresPermission);
        PermissionChecker permissionChecker = permissionCheckerFactory.getPermissionChecker(resourceType);
        permissionChecker.hasPermission(resourceId, actionType, currentUser);
    }

    private Object getResourceId(JoinPoint joinPoint, RequiresPermission requiresPermission) {
        String resourceIdParamName = requiresPermission.resourceIdParamName();
        if(resourceIdParamName.equals("none")) {
            return null;
        }
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Parameter[] parameters = method.getParameters();
        int resourceIndex = -1;
        for(int i = 0; i < parameters.length; i++) {
            if(parameters[i].getName().equals(resourceIdParamName)) {
                resourceIndex = i;
                break;
            }
        }

        return joinPoint.getArgs()[resourceIndex];
    }

    private ActionType getActionType(JoinPoint joinPoint, RequiresPermission requiresPermission) {
        ActionType actionType = requiresPermission.actionType();
        if(!actionType.equals(ActionType.GET_FROM_REQUEST_MAPPING)) {
            return actionType;
        }
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
       RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
       if(requestMapping == null) {
           throw new BadPermissionAccessConfiguration("Method has no @RequestMapping annotation");
       }
       RequestMethod httpMethod = requestMapping.method()[0];
       switch (httpMethod) {
           case GET:
               return ActionType.VIEW;
           case POST:
               return ActionType.CREATE;
           case PUT:
           case PATCH:
               return ActionType.UPDATE;
           case DELETE:
               return ActionType.DELETE;
           default:
               throw new BadPermissionAccessConfiguration("Unsupported HTTP method = %s".formatted(httpMethod.toString()));
        }
    }

    private ResourceType getResourceType(JoinPoint joinPoint, RequiresPermission requiresPermission) {
        Class<?> clazz = joinPoint.getTarget().getClass();
        ResourceType resourceType = requiresPermission.resourceType();
        if(resourceType.equals(ResourceType.GET_FROM_CLASS_NAME)) {
            resourceType = resourceType.resolveResourceTypeFromClass(clazz);
        }
        return resourceType;
    }

}
