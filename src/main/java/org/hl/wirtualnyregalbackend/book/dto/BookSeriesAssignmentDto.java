package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import org.hl.wirtualnyregalbackend.book_series.dto.BookSeriesMutationDto;

public record BookSeriesAssignmentDto(
    Long id,

    @JsonUnwrapped
    @Valid
    BookSeriesMutationDto bookSeriesDto,

    @Min(0)
    Integer bookOrder
) {

    @AssertTrue(message = "Provide either book series ID or book series details.")
    public boolean isValid() {
        return (id != null) && (bookSeriesDto != null);
    }

}
