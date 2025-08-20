package org.hl.wirtualnyregalbackend.challenge.model;

import jakarta.persistence.Embeddable;

import java.time.Instant;

@Embeddable
public record ChallengeDurationRange(
    Instant startAt,
    Instant endAt
) {


}
