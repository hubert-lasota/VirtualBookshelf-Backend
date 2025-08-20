package org.hl.wirtualnyregalbackend.challenge.dto;


import org.hl.wirtualnyregalbackend.common.model.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record ChallengePageResponse(
    List<ChallengeResponse> challenges,
    PageMeta pageMeta
) {

    public static ChallengePageResponse from(Page<ChallengeResponse> page) {
        return new ChallengePageResponse(page.getContent(), PageMeta.from(page));
    }

}
