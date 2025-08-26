package org.hl.wirtualnyregalbackend.book.dto;

import org.hl.wirtualnyregalbackend.common.pagination.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record BookPageResponse(
    List<BookResponse> books,
    PageMeta pageMeta
) {

    public static BookPageResponse from(Page<BookResponse> page) {
        return new BookPageResponse(page.getContent(), PageMeta.from(page));
    }

}
