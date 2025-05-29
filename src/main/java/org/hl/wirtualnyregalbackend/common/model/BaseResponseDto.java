package org.hl.wirtualnyregalbackend.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public abstract class BaseResponseDto {

    @JsonProperty
    private final Long id;
    @JsonProperty
    private final Instant createdAt;
    @JsonProperty
    private final Instant updatedAt;

    public BaseResponseDto(Long id, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
