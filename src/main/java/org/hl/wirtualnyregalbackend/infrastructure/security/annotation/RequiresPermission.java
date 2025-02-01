package org.hl.wirtualnyregalbackend.infrastructure.security.annotation;

import org.hl.wirtualnyregalbackend.application.common.ActionType;
import org.hl.wirtualnyregalbackend.infrastructure.common.ResourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermission {

    String resourceIdParamName() default "none";

    ActionType actionType() default ActionType.GET_FROM_REQUEST_MAPPING;

    ResourceType resourceType() default ResourceType.GET_FROM_CLASS_NAME;

}
