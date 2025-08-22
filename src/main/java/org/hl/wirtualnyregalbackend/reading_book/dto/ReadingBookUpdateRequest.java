package org.hl.wirtualnyregalbackend.reading_book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;

@Getter
@Setter
@NoArgsConstructor
public class ReadingBookUpdateRequest {

    @NotNull(groups = CreateGroup.class)
    @Valid
    protected BookWithIdDto book;

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    protected ReadingStatus status;

    @Valid
    @NotNull(groups = CreateGroup.class)
    protected ReadingBookDurationRange durationRange;

}
