package org.hl.wirtualnyregalbackend.publisher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.common.model.BaseResponseDto;

import java.time.Instant;

public class PublisherResponseDto extends BaseResponseDto {

    @JsonUnwrapped
    @JsonProperty
    private final PublisherMutationDto publisherDto;


    public PublisherResponseDto(Long id,
                                Instant createdAt,
                                Instant updatedAt,
                                PublisherMutationDto publisherDto) {
        super(id, createdAt, updatedAt);
        this.publisherDto = publisherDto;
    }
}
