package org.hl.wirtualnyregalbackend.common.model;

public record ApiFieldError(
    String field,
    String message,
    Object rejectedValue
) {
}
