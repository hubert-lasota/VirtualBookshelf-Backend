package org.hl.wirtualnyregalbackend.security.annotation;

import org.hl.wirtualnyregalbackend.common.ActionType;
import org.hl.wirtualnyregalbackend.common.ResourceType;

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
