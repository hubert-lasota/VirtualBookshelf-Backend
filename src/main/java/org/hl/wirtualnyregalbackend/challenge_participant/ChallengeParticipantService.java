package org.hl.wirtualnyregalbackend.challenge_participant;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class ChallengeParticipantService {

    private final ChallengeParticipantRepository participantRepository;


}
