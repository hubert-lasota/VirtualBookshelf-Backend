package org.hl.wirtualnyregalbackend.auth.permission;

import org.springframework.security.core.Authentication;

class PermissionUtils {

    private PermissionUtils() {
    }

    public static boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
    }

}
