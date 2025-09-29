package org.hl.wirtualnyregalbackend.auth.permission;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_session.ReadingSessionQueryService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReadingSessionPermissionEvaluator implements ResourcePermissionEvaluator {

    private final ReadingSessionQueryService query;

    @Override
    public boolean hasPermission(User user, Object targetId, ActionType actionType) {
        Long sessionId = (Long) targetId;
        return query.isSessionAuthor(sessionId, user);
    }

}
