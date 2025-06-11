package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import org.hl.wirtualnyregalbackend.genre.dto.GenreMutationDto;

public record GenreWithIdDto(
    Long id,
    @JsonUnwrapped
    @Valid
    GenreMutationDto genreDto
) {

    @AssertTrue(message = "Provide either genre ID or genre details.")
    public boolean isValid() {
        return (id != null) && (genreDto != null);
    }

}
