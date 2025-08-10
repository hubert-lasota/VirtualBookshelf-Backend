package org.hl.wirtualnyregalbackend.genre.dto;

import org.hl.wirtualnyregalbackend.common.model.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record GenrePageResponseDto(
    List<GenreResponseDto> genres,
    PageMeta pageMeta
) {

    public static GenrePageResponseDto from(Page<GenreResponseDto> page) {
        return new GenrePageResponseDto(page.getContent(), PageMeta.from(page));
    }

}
