package org.hl.wirtualnyregalbackend.bookshelf_book.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookshelfBookWithBookshelfId {

    @NotNull
    private Long bookshelfId;

    @JsonUnwrapped
    @Valid
    private BookshelfBookMutationDto bookshelfBookDto;

}
