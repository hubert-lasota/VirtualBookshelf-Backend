package org.hl.wirtualnyregalbackend.user.dto;

import org.hl.wirtualnyregalbackend.common.pagination.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record UserPageResponse(
    List<UserResponse> users,
    PageMeta pageMeta
) {

    public static UserPageResponse from(Page<UserResponse> page) {
        return new UserPageResponse(page.getContent(), PageMeta.from(page));
    }

}
