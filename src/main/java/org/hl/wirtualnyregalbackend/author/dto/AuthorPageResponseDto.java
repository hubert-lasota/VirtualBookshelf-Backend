package org.hl.wirtualnyregalbackend.author.dto;

import org.hl.wirtualnyregalbackend.common.model.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record AuthorPageResponseDto(
    List<AuthorResponseDto> authors,
    PageMeta pageMeta
) {

    public static AuthorPageResponseDto from(Page<AuthorResponseDto> page) {
        return new AuthorPageResponseDto(page.getContent(), PageMeta.from(page));
    }

}
