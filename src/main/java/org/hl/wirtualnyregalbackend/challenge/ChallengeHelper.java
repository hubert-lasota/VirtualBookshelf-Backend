package org.hl.wirtualnyregalbackend.challenge;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge.exception.ChallengeNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class ChallengeHelper {

    private final ChallengeRepository challengeRepository;

    public Challenge findChallengeById(Long challengeId) throws ChallengeNotFoundException {
        Optional<Challenge> challengeOpt = challengeId == null
            ? Optional.empty()
            : challengeRepository.findById(challengeId);
        return challengeOpt.orElseThrow(() -> {
            log.warn("Challenge not found with ID: {}", challengeId);
            return new ChallengeNotFoundException(challengeId);
        });
    }

}
