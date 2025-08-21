package org.hl.wirtualnyregalbackend.reading_session.dto;

import org.hl.wirtualnyregalbackend.common.pagination.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record ReadingSessionPageResponse(List<ReadingSessionResponse> sessions, PageMeta pageMeta) {

    public static ReadingSessionPageResponse from(Page<ReadingSessionResponse> page) {
        return new ReadingSessionPageResponse(page.getContent(), PageMeta.from(page));
    }
}
