package org.hl.wirtualnyregalbackend.reading_book.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.book.dto.BookRequest;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookWithIdDto {

    private Long id;

    @JsonUnwrapped
    // TODO fix later @Valid
    private BookRequest bookRequest;

    @AssertTrue(message = "Provide either book ID or book details.")
    public boolean isValid() {
        return (id != null) || (bookRequest != null);
    }

}
