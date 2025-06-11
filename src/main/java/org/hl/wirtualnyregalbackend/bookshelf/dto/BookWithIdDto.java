package org.hl.wirtualnyregalbackend.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import org.hl.wirtualnyregalbackend.book.dto.BookMutationDto;

public record BookWithIdDto(
    Long id,
    @JsonUnwrapped
    @Valid
    BookMutationDto bookDto) {

    @AssertTrue(message = "Provide either book ID or book details.")
    public boolean isValid() {
        return (id != null) || (bookDto != null);
    }

}
