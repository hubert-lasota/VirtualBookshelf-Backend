package org.hl.wirtualnyregalbackend.challenge_participant.dto;

import org.hl.wirtualnyregalbackend.common.pagination.PageMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public record ChallengeParticipantPageResponse(List<ChallengeParticipantResponse> participants, PageMeta pageMeta) {

    public static ChallengeParticipantPageResponse from(Page<ChallengeParticipantResponse> page) {
        return new ChallengeParticipantPageResponse(page.getContent(), PageMeta.from(page));
    }

}
