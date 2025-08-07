package org.hl.wirtualnyregalbackend.reading_book.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.RangeDateValidator;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingStatus;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
abstract class BaseReadingBookDto {

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    protected ReadingStatus status;

    @JsonProperty
    protected Instant startedReadingAt;

    @JsonProperty
    protected Instant finishedReadingAt;

    @JsonIgnore
    @AssertTrue(message = "startedReadingAt must be before finishedReadingAt")
    public boolean isValid() {
        return RangeDateValidator.isValid(startedReadingAt, finishedReadingAt);
    }

}
