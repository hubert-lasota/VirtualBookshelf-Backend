package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;

public record AuthorWithIdDto(
    Long id,

    @JsonUnwrapped
    @Valid
    AuthorMutationDto authorDto
) {

    @AssertTrue(message = "Provide either author ID or author details.")
    public boolean isValid() {
        return (id != null) && (authorDto != null);
    }

}
