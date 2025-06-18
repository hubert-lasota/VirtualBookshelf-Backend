package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.book_series.dto.BookSeriesMutationDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookSeriesAssignmentDto {

    private Long id;

    @JsonUnwrapped
    @Valid
    private BookSeriesMutationDto bookSeriesDto;

    @Min(0)
    private Integer bookOrder;

    @AssertTrue(message = "Provide either book series ID or book series details.")
    public boolean isValid() {
        return (id != null) && (bookSeriesDto != null);
    }

}
