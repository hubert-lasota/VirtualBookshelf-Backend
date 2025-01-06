package org.hl.wirtualnyregalbackend.infrastructure.security.annotation;

import org.hl.wirtualnyregalbackend.infrastructure.security.ActionType;
import org.hl.wirtualnyregalbackend.infrastructure.security.ResourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermission {

    ActionType value();

    /// If ResourceType is equal to NONE PermissionChecker will fetch ResourceType from @PermissionAccessResource annotation
    ResourceType resourceType() default ResourceType.NONE;
}
