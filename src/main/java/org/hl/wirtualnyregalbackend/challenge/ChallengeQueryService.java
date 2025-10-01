package org.hl.wirtualnyregalbackend.challenge;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengePageResponse;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge.exception.ChallengeNotFoundException;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeFilter;
import org.hl.wirtualnyregalbackend.challenge_participant.ChallengeParticipantQueryService;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class ChallengeQueryService {

    private final ChallengeRepository repository;
    private final ChallengeParticipantQueryService participantQuery;

    public Challenge findChallengeById(Long challengeId) throws ChallengeNotFoundException {
        Optional<Challenge> challengeOpt = challengeId == null
            ? Optional.empty()
            : repository.findById(challengeId);
        return challengeOpt.orElseThrow(() -> {
            log.warn("Challenge not found with ID: {}", challengeId);
            return new ChallengeNotFoundException(challengeId);
        });
    }

    ChallengePageResponse findChallenges(ChallengeFilter filter, User participant, Pageable pageable) {
        var spec = ChallengeSpecification.byFilterAndParticipant(filter, participant);
        var page = repository.findAll(spec, pageable);
        List<Long> challengeIds = page
            .getContent()
            .stream()
            .map(Challenge::getId)
            .toList();
        Map<Long, ChallengeParticipant> participants = participantQuery.findCurrentUserParticipantsByChallengeIds(challengeIds, participant);
        Locale locale = LocaleContextHolder.getLocale();
        var responsePage = page.map((challenge) -> {
            var p = participants.get(challenge.getId());
            return ChallengeMapper.toChallengeResponse(challenge, p, locale);
        });
        return ChallengePageResponse.from(responsePage);
    }

    public boolean isChallengeAuthor(Long challengeId, User user) {
        return repository.isChallengeAuthor(challengeId, user.getId());
    }

    public Slice<Challenge> findChallengesByEndAt(LocalDate endAt, Pageable pageable) {
        return repository.findByEndAt(endAt, pageable);
    }

}
