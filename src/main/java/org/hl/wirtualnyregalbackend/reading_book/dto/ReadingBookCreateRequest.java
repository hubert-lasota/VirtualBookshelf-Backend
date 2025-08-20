package org.hl.wirtualnyregalbackend.reading_book.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadingBookCreateRequest extends ReadingBookUpdateRequest {

    @NotNull
    private Long bookshelfId;

}
