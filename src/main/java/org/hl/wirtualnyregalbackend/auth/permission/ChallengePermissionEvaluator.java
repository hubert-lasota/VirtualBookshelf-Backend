package org.hl.wirtualnyregalbackend.auth.permission;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.ChallengeService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChallengePermissionEvaluator implements ResourcePermissionEvaluator {

    private final ChallengeService challengeService;

    @Override
    public boolean hasPermission(User user, Object targetId, ActionType actionType) {
        Long challengeId = (Long) targetId;
        return challengeService.isChallengeAuthor(challengeId, user);
    }

}
