package org.hl.wirtualnyregalbackend.common.review;

import org.hl.wirtualnyregalbackend.common.model.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record ReviewPageResponse(
    List<ReviewResponse> reviews,
    PageMeta pageMeta
) {

    public static ReviewPageResponse from(Page<ReviewResponse> page) {
        return new ReviewPageResponse(page.getContent(), PageMeta.from(page));
    }

}
