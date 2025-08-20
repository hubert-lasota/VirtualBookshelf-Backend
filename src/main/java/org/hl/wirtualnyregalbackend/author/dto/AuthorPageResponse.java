package org.hl.wirtualnyregalbackend.author.dto;

import org.hl.wirtualnyregalbackend.common.model.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record AuthorPageResponse(
    List<AuthorResponse> authors,
    PageMeta pageMeta
) {

    public static AuthorPageResponse from(Page<AuthorResponse> page) {
        return new AuthorPageResponse(page.getContent(), PageMeta.from(page));
    }

}
