package org.hl.wirtualnyregalbackend.user;

import jakarta.persistence.criteria.Expression;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.user.model.UserFilter;
import org.springframework.data.jpa.domain.Specification;

class UserSpecification {

    private UserSpecification() {
    }

    public static Specification<User> byFilter(UserFilter filter) {
        Specification<User> spec = Specification.where(null);
        if (filter.query() != null) {
            spec = spec.and(byQuery(filter.query()));
        }

        return spec;
    }

    private static Specification<User> byQuery(String query) {
        return (root, cq, cb) -> {
            Expression<String> username = cb.lower(root.get("username"));
            Expression<String> firstName = cb.lower(root.get("userProfile").get("firstName"));
            Expression<String> lastName = cb.lower(root.get("userProfile").get("lastName"));
            String pattern = "%" + query.toLowerCase() + "%";
            return cb.or(cb.like(username, pattern), cb.like(firstName, pattern), cb.like(lastName, pattern));
        };
    }
}
