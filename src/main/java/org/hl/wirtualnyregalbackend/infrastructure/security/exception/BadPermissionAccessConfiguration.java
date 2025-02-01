package org.hl.wirtualnyregalbackend.infrastructure.security.exception;

public class BadPermissionAccessConfiguration extends RuntimeException {

    public BadPermissionAccessConfiguration(String message) {
        super(message);
    }

}
