package org.hl.wirtualnyregalbackend.challenge.dto;


import org.hl.wirtualnyregalbackend.common.model.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record ChallengePageResponseDto(
    List<ChallengeResponseDto> challenges,
    PageMeta pageMeta
) {

    public static ChallengePageResponseDto from(Page<ChallengeResponseDto> page) {
        return new ChallengePageResponseDto(page.getContent(), PageMeta.from(page));
    }

}
