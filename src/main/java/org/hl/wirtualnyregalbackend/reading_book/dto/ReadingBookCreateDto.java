package org.hl.wirtualnyregalbackend.reading_book.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadingBookCreateDto extends ReadingBookUpdateDto {

    @NotNull
    private Long bookshelfId;

}
