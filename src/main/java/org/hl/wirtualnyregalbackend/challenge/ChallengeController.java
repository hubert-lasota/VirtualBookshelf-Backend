package org.hl.wirtualnyregalbackend.challenge;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengePageResponse;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeRequest;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeResponse;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeFilter;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/challenges")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ChallengeController {

    private final ChallengeCommandService challengeService;
    private final ChallengeQueryService query;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChallengeResponse createChallenge(@Validated(CreateGroup.class)
                                             @RequestBody
                                             ChallengeRequest challengeRequest,
                                             @AuthenticationPrincipal
                                             User user) {
        return challengeService.createChallenge(challengeRequest, user);
    }

    @PatchMapping("/{challengeId}")
    @PreAuthorize("hasPermission(#challengeId, 'CHALLENGE', 'UPDATE')")
    public ChallengeResponse updateChallenge(@PathVariable
                                             Long challengeId,
                                             @Validated(UpdateGroup.class)
                                             @RequestBody
                                             ChallengeRequest challengeRequest) {
        return challengeService.updateChallenge(challengeId, challengeRequest);
    }

    @DeleteMapping("/{challengeId}/quit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void quitChallenge(@PathVariable Long challengeId, @AuthenticationPrincipal User user) {
        challengeService.quitChallenge(challengeId, user);
    }

    @GetMapping
    public ChallengePageResponse findChallenges(@Valid ChallengeFilter filter,
                                                Pageable pageable,
                                                @AuthenticationPrincipal User user) {
        return query.findChallenges(filter, user, pageable);
    }

}
