package org.hl.wirtualnyregalbackend.bookshelf_book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookReadingStatus;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.RangeDateValidator;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
abstract class BaseBookshelfBookDto {

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    @Min(1)
    protected Integer currentPage;

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    protected BookReadingStatus status;

    @JsonProperty
    protected Instant startedReadingAt;

    @JsonProperty
    protected Instant finishedReadingAt;


    @AssertTrue(message = "startedReadingAt must be before finishedReadingAt")
    public boolean isValid() {
        return RangeDateValidator.isValid(startedReadingAt, finishedReadingAt);
    }

}
