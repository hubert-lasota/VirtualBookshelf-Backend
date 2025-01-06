package org.hl.wirtualnyregalbackend.infrastructure.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.hl.wirtualnyregalbackend.infrastructure.security.annotation.PermissionAccessResource;
import org.hl.wirtualnyregalbackend.infrastructure.security.annotation.RequiresPermission;
import org.hl.wirtualnyregalbackend.infrastructure.security.annotation.ResourceId;
import org.hl.wirtualnyregalbackend.infrastructure.security.exception.BadPermissionAccessConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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
        Class<?> clazz = joinPoint.getTarget().getClass();
        ResourceType resourceType = requiresPermission.resourceType();
        if(resourceType.equals(ResourceType.NONE)) {
            try {
                resourceType = clazz.getAnnotation(PermissionAccessResource.class).value();
            } catch (NullPointerException e) {
                throw new BadPermissionAccessConfiguration(
                        "There is no annotation @PermissionAccessResource on target class and no resourceType value on method", e);
            }
        }
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Parameter[] parameters = method.getParameters();
        int resourceIndex = -1;
        boolean hasResourceId = false;
        for(int i = 0; i < parameters.length; i++) {
            ResourceId idAnnotation = parameters[i].getAnnotation(ResourceId.class);
            if(idAnnotation != null) {
                resourceIndex = i;
                if(hasResourceId) {
                    throw new BadPermissionAccessConfiguration("Multiple ResourceId annotations found on method " + method.getName());
                }
                hasResourceId = true;
            }
        }

        Object resourceId = null;
        if(resourceIndex != -1) {
            Object[] args = joinPoint.getArgs();
            resourceId = args[resourceIndex];
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        ActionType actionType = requiresPermission.value();
        PermissionChecker permissionChecker = permissionCheckerFactory.getPermissionChecker(resourceType);
        permissionChecker.hasPermission(resourceId, actionType, currentUser);
    }

}
