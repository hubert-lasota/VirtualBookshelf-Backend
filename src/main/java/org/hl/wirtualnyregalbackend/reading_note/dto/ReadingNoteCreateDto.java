package org.hl.wirtualnyregalbackend.reading_note.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadingNoteCreateDto extends ReadingNoteMutationDto {

    @NotNull
    private Long readingBookId;

}
