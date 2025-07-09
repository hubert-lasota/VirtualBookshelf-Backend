package org.hl.wirtualnyregalbackend.reading_book.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;

@Getter
@Setter
@NoArgsConstructor
public class ReadingBookMutationDto extends BaseReadingBookDto {

    @NotNull(groups = CreateGroup.class)
    @Valid
    protected BookWithIdDto book;

}
