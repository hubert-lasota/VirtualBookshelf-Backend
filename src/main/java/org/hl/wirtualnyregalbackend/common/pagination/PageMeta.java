package org.hl.wirtualnyregalbackend.common.pagination;

import org.springframework.data.domain.Page;

public record PageMeta(
    int count,
    long totalCount,
    int page,
    int totalPages
) {

    public static PageMeta from(Page<?> page) {
        return new PageMeta(
            page.getNumberOfElements(),
            page.getTotalElements(),
            page.getNumber(),
            page.getTotalPages()
        );
    }

}
