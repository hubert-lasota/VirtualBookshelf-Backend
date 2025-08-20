package org.hl.wirtualnyregalbackend.book.dto;

import org.hl.wirtualnyregalbackend.common.model.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record BookPageResponseDto(
    List<BookResponse> books,
    PageMeta pageMeta
) {

    public static BookPageResponseDto from(Page<BookResponse> page) {
        return new BookPageResponseDto(page.getContent(), PageMeta.from(page));
    }

}
