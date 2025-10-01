package org.hl.wirtualnyregalbackend.challenge;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeFilter;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeType;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

class ChallengeSpecification {

    private ChallengeSpecification() {
    }

    public static Specification<Challenge> byFilterAndParticipant(ChallengeFilter filter, User participant) {
        Specification<Challenge> spec = Specification.where(null);
        if (filter.participating() != null) {
            spec = ChallengeSpecification.byParticipating(participant, filter.participating());
        }

        if (filter.query() != null) {
            spec = spec.and(ChallengeSpecification.byQuery(filter.query()));
        }

        if (filter.durationRange() != null) {
            Instant lte = filter.durationRange().lte();
            if (lte != null) {
                spec = spec.and(ChallengeSpecification.lteEndAt(lte));
            }

            Instant gte = filter.durationRange().gte();
            if (gte != null) {
                spec = spec.and(ChallengeSpecification.gteStartAt(gte));
            }
        }

        if (filter.type() != null) {
            spec = spec.and(ChallengeSpecification.byType(filter.type()));
        }

        return spec;
    }

    private static Specification<Challenge> byParticipating(User user, boolean participating) {
        return (root, query, cb) -> {
            Subquery<Long> sq = query.subquery(Long.class);
            Root<ChallengeParticipant> sqRoot = sq.from(ChallengeParticipant.class);

            sq.select(sqRoot.get("challenge").get("id"))
                .where(cb.equal(sqRoot.get("user"), user), cb.equal(sqRoot.get("challenge"), root));

            return participating
                ? root.get("id").in(sq)
                : cb.not(root.get("id").in(sq));
        };
    }

    private static Specification<Challenge> byQuery(String query) {
        return (root, criteriaQuery, cb) -> {
            String pattern = "%" + query.toLowerCase() + "%";
            return cb.or(cb.like(cb.lower(root.get("title")), pattern), cb.like(cb.lower(root.get("description")), pattern));
        };
    }

    private static Specification<Challenge> byType(ChallengeType type) {
        return (root, query, cb) -> cb.equal(root.get("type"), type);
    }

    private static Specification<Challenge> lteEndAt(Instant lte) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("endAt"), lte);
    }

    private static Specification<Challenge> gteStartAt(Instant gte) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("startAt"), gte);
    }

}

