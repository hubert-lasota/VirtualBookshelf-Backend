package org.hl.wirtualnyregalbackend.common.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.common.model.BaseResponseDto;
import org.hl.wirtualnyregalbackend.user.model.dto.UserHeaderResponseDto;

import java.time.Instant;

public abstract class ReviewResponseDto extends BaseResponseDto {

    @JsonProperty
    private final UserHeaderResponseDto user;
    @JsonUnwrapped
    @JsonProperty
    private final ReviewDto review;

    public ReviewResponseDto(Long id,
                             Instant createdAt,
                             Instant updatedAt,
                             UserHeaderResponseDto user,
                             ReviewDto review) {
        super(id, createdAt, updatedAt);
        this.user = user;
        this.review = review;
    }

}
