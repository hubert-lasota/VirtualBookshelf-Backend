package org.hl.wirtualnyregalbackend.genre.dto;

import org.hl.wirtualnyregalbackend.common.pagination.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record GenrePageResponse(
    List<GenreResponse> genres,
    PageMeta pageMeta
) {

    public static GenrePageResponse from(Page<GenreResponse> page) {
        return new GenrePageResponse(page.getContent(), PageMeta.from(page));
    }

}
