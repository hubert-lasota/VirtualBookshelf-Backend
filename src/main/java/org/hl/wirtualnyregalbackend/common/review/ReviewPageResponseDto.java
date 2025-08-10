package org.hl.wirtualnyregalbackend.common.review;

import org.hl.wirtualnyregalbackend.common.model.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record ReviewPageResponseDto(
    List<ReviewResponseDto> reviews,
    PageMeta pageMeta
) {

    public static ReviewPageResponseDto from(Page<ReviewResponseDto> page) {
        return new ReviewPageResponseDto(page.getContent(), PageMeta.from(page));
    }
}
