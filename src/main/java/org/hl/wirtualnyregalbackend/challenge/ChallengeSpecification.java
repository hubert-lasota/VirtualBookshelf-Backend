package org.hl.wirtualnyregalbackend.challenge;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.springframework.data.jpa.domain.Specification;

class ChallengeSpecification {

    private ChallengeSpecification() {
    }

    public static Specification<Challenge> byParticipant(User user) {
        return (root, query, cb) -> {
            Subquery<Long> sq = query.subquery(Long.class);
            Root<ChallengeParticipant> cpRoot = sq.from(ChallengeParticipant.class);
            sq.select(cpRoot.get("challenge").get("id"))
                .where(cb.equal(cpRoot.get("user"), user), cb.equal(cpRoot.get("challenge"), root));

            return root.get("id").in(sq);
        };
    }

}

