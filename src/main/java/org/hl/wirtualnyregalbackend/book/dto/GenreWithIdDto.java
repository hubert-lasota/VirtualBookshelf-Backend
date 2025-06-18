package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.genre.dto.GenreMutationDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreWithIdDto {

    private Long id;

    @JsonUnwrapped
    @Valid
    private GenreMutationDto genreDto;

    @AssertTrue(message = "Provide either genre ID or genre details.")
    public boolean isValid() {
        return (id != null) && (genreDto != null);
    }

}
