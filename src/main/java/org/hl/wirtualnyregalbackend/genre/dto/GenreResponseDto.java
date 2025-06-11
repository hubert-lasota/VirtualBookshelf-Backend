package org.hl.wirtualnyregalbackend.genre.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.common.model.BaseResponseDto;

import java.time.Instant;

public class GenreResponseDto extends BaseResponseDto {

    @JsonProperty
    @JsonUnwrapped
    private final GenreMutationDto genreMutationDto;

    public GenreResponseDto(Long id,
                            Instant createdAt,
                            Instant updatedAt,
                            GenreMutationDto genreMutationDto) {
        super(id, createdAt, updatedAt);
        this.genreMutationDto = genreMutationDto;
    }

}
