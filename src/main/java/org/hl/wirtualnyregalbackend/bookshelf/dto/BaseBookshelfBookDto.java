package org.hl.wirtualnyregalbackend.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookReadingStatus;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
abstract class BaseBookshelfBookDto {

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    @Min(1)
    protected final Integer currentPage;

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    protected final BookReadingStatus status;

    @JsonProperty
    @JsonUnwrapped
    @NotNull(groups = CreateGroup.class)
    @Valid
    protected final RangeDate rangeDate;

    @JsonProperty
    @Valid
    protected final List<BookshelfBookNoteDto> notes;

}
