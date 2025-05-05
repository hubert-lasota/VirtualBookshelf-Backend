package org.hl.wirtualnyregalbackend.book.model.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.book.model.dto.BookMutationDto;

public record BookResponseDto(
    Long id,
    @JsonUnwrapped
    BookMutationDto bookDto
) {
}
