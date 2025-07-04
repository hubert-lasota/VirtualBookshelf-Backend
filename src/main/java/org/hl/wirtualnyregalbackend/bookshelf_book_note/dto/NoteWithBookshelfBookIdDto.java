package org.hl.wirtualnyregalbackend.bookshelf_book_note.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoteWithBookshelfBookIdDto {

    @NotNull
    private Long bookshelfBookId;

    @NotNull
    @Valid
    @JsonUnwrapped
    private BookshelfBookNoteMutationDto noteDto;

}
