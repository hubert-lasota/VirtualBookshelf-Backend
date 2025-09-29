package org.hl.wirtualnyregalbackend.reading_session;

import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.hl.wirtualnyregalbackend.reading_session.model.ReadingSessionFilter;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

class ReadingSessionSpecification {

    private ReadingSessionSpecification() {
    }

    public static Specification<ReadingSession> byFilter(ReadingSessionFilter filter) {
        var spec = byReadingBookId(filter.readingBookId());
        if (filter.query() != null) {
            spec = spec.and(byQuery(filter.query()));
        }

        if (filter.durationRange() != null) {
            Instant lte = filter.durationRange().lte();
            if (lte != null) {
                spec = spec.and(lteFinishedAt(lte));
            }

            Instant gte = filter.durationRange().gte();
            if (gte != null) {
                spec = spec.and(gteStartedAt(gte));
            }
        }

        if (filter.pageRange() != null) {
            Integer lte = filter.pageRange().lte();
            if (lte != null) {
                spec = spec.and(ltePageTo(lte));
            }

            Integer gte = filter.pageRange().gte();
            if (gte != null) {
                spec = spec.and(gtePageFrom(gte));
            }
        }

        return spec;
    }

    private static Specification<ReadingSession> byReadingBookId(Long readingBookId) {
        return (root, query, cb) -> cb.equal(root.get("readingBook").get("id"), readingBookId);
    }

    private static Specification<ReadingSession> byQuery(String query) {
        return (root, cq, cb) -> {
            String pattern = "%" + query.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("title")), pattern);
        };
    }

    private static Specification<ReadingSession> gteStartedAt(Instant gte) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("durationRange").get("startedAt"), gte);
    }

    private static Specification<ReadingSession> lteFinishedAt(Instant lte) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("durationRange").get("finishedAt"), lte);
    }

    private static Specification<ReadingSession> gtePageFrom(int gte) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("pageRange").get("from"), gte);
    }

    private static Specification<ReadingSession> ltePageTo(int lte) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("pageRange").get("to"), lte);
    }

}
