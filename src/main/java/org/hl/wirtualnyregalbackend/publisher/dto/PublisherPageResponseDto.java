package org.hl.wirtualnyregalbackend.publisher.dto;

import org.hl.wirtualnyregalbackend.common.model.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record PublisherPageResponseDto(
    List<PublisherResponseDto> publishers,
    PageMeta pageMeta
) {

    public static PublisherPageResponseDto from(Page<PublisherResponseDto> page) {
        return new PublisherPageResponseDto(page.getContent(), PageMeta.from(page));
    }

}
