package org.hl.wirtualnyregalbackend.challenge_participant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge_participant.dto.ChallengeParticipantPageResponse;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.challenge_participant.exception.ChallengeParticipantNotFoundException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class ChallengeParticipantQueryService {

    private final ChallengeParticipantRepository repository;


    public ChallengeParticipantPageResponse findParticipantsByChallengeId(Long challengeId, Pageable pageable) {
        Locale locale = LocaleContextHolder.getLocale();
        var page = repository
            .findByChallengeId(challengeId, pageable)
            .map(participant -> ChallengeParticipantMapper.toChallengeParticipantResponse(participant, locale));
        return ChallengeParticipantPageResponse.from(page);
    }

    public Map<Long, ChallengeParticipant> findCurrentUserParticipantsByChallengeIds(List<Long> challengeIds, User currentUser) {
        return repository
            .findByChallengeIdsAndUserId(challengeIds, currentUser.getId())
            .stream()
            .collect(Collectors.toMap(p -> p.getChallenge().getId(), p -> p));
    }

    public ChallengeParticipant findParticipantByChallengeIdAndUserId(Long challengeId, Long userId)
        throws ChallengeParticipantNotFoundException {
        return repository.findByChallengeIdAndUserId(challengeId, userId)
            .orElseThrow(() -> {
                String message = "ChallengeParticipant not found with challengeId: %s and userId: %s".formatted(challengeId, userId);
                log.warn(message);
                return new ChallengeParticipantNotFoundException(message);
            });
    }


    public ChallengeParticipant findParticipantById(Long participantId) throws ChallengeParticipantNotFoundException {
        Optional<ChallengeParticipant> participantOpt = participantId != null ? repository.findById(participantId) : Optional.empty();
        return participantOpt.orElseThrow(() -> {
            log.warn("ChallengeParticipant not found with ID: {}", participantId);
            return new ChallengeParticipantNotFoundException(participantId);
        });
    }

}
