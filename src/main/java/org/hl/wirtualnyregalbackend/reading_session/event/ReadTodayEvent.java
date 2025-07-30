package org.hl.wirtualnyregalbackend.reading_session.event;

import org.hl.wirtualnyregalbackend.auth.entity.User;

import java.time.Instant;

public record ReadTodayEvent(Instant lastReadAt, User user) {
}
