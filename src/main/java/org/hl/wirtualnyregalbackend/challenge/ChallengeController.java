package org.hl.wirtualnyregalbackend.challenge;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeMutationDto;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengePageResponseDto;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeResponseDto;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeFilter;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/challenges")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChallengeResponseDto createChallenge(@Validated(CreateGroup.class)
                                                @RequestBody
                                                ChallengeMutationDto challengeDto,
                                                @AuthenticationPrincipal
                                                User user) {
        return challengeService.createChallenge(challengeDto, user);
    }

    @PatchMapping("/{challengeId}")
    public ChallengeResponseDto updateChallenge(@PathVariable
                                                Long challengeId,
                                                @Validated(UpdateGroup.class)
                                                @RequestBody
                                                ChallengeMutationDto challengeDto) {
        return challengeService.updateChallenge(challengeId, challengeDto);
    }

    @GetMapping
    public ChallengePageResponseDto findChallenges(ChallengeFilter filter,
                                                   Pageable pageable,
                                                   @AuthenticationPrincipal User user) {
        return challengeService.findChallenges(filter, user, pageable);
    }

    @DeleteMapping("/{challengeId}/quit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void quitChallenge(@PathVariable Long challengeId) {
        challengeService.quitChallenge(challengeId);
    }

}
