package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherMutationDto;

public record PublisherWithIdDto(
    Long id,
    @JsonUnwrapped
    @Valid
    PublisherMutationDto publisherDto
) {

    @AssertTrue(message = "Provide either publisher ID or publisher details.")
    public boolean isValid() {
        return (id != null) && (publisherDto != null);
    }

}
