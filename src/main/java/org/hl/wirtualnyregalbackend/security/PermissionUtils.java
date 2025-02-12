package org.hl.wirtualnyregalbackend.security;

import org.hl.wirtualnyregalbackend.security.model.Authority;
import org.hl.wirtualnyregalbackend.security.model.AuthorityType;
import org.hl.wirtualnyregalbackend.security.model.User;

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
