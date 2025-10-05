package org.hl.wirtualnyregalbackend.reading_statistics;

import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

class StatisticsSpecification {

    private StatisticsSpecification() {
    }

    public static <T> Specification<T> byUserId(Long userId) {
        return ((root, query, cb) -> cb.equal(root.get("user").get("id"), userId));
    }

    public static <T> Specification<T> byYear(int year) {
        return (root, query, cb) -> {
            Expression<Integer> yearExp = cb.function("EXTRACT", Integer.class, cb.literal("YEAR"), root.get("yearMonth"));
            return cb.equal(yearExp, year);
        };
    }

}
