package org.hl.wirtualnyregalbackend.book.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.common.model.BaseResponseDto;

import java.time.Instant;

public class BookResponseDto extends BaseResponseDto {

    @JsonUnwrapped
    @JsonProperty
    private final BookMutationDto bookMutationDto;

    public BookResponseDto(Long id,
                           Instant createdAt,
                           Instant updatedAt,
                           BookMutationDto bookMutationDto) {
        super(id, createdAt, updatedAt);
        this.bookMutationDto = bookMutationDto;
    }

}
