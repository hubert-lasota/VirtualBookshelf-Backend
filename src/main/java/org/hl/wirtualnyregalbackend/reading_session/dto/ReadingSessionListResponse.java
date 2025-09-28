package org.hl.wirtualnyregalbackend.reading_session.dto;

import java.util.List;

public record ReadingSessionListResponse(List<ReadingSessionResponse> sessions) {
}
