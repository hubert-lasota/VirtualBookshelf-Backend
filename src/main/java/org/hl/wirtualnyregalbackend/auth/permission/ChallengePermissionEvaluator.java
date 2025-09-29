package org.hl.wirtualnyregalbackend.auth.permission;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.ChallengeQueryService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChallengePermissionEvaluator implements ResourcePermissionEvaluator {

    private final ChallengeQueryService query;

    @Override
    public boolean hasPermission(User user, Object targetId, ActionType actionType) {
        Long challengeId = (Long) targetId;
        return query.isChallengeAuthor(challengeId, user);
    }

}
