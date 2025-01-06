package org.hl.wirtualnyregalbackend.infrastructure.security;

import java.util.Collection;
import java.util.List;

public class PermissionUtils {

    private PermissionUtils() { }

    public static boolean hasUserAuthority(User user, AuthorityType authorityType) {
       Collection<Authority> authorities = (Collection<Authority>) user.getAuthorities();
       List<AuthorityType> roles = authorities.stream()
               .map(Authority::getAuthorityType)
               .toList();
       return roles.contains(authorityType);
    }

}
