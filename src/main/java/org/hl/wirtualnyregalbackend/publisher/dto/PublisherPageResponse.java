package org.hl.wirtualnyregalbackend.publisher.dto;

import org.hl.wirtualnyregalbackend.common.model.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record PublisherPageResponse(
    List<PublisherResponse> publishers,
    PageMeta pageMeta
) {

    public static PublisherPageResponse from(Page<PublisherResponse> page) {
        return new PublisherPageResponse(page.getContent(), PageMeta.from(page));
    }

}
