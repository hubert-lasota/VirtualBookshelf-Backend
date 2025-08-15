package org.hl.wirtualnyregalbackend.reading_session.dto;

import org.hl.wirtualnyregalbackend.common.model.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record ReadingSessionPageResponseDto(List<ReadingSessionResponseDto> sessions, PageMeta pageMeta) {

    public static ReadingSessionPageResponseDto from(Page<ReadingSessionResponseDto> page) {
        return new ReadingSessionPageResponseDto(page.getContent(), PageMeta.from(page));
    }
}
