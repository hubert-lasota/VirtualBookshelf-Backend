package org.hl.wirtualnyregalbackend.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.book.dto.BookMutationDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookWithIdDto {

    private Long id;

    @JsonUnwrapped
    @Valid
    private BookMutationDto bookDto;

    @AssertTrue(message = "Provide either book ID or book details.")
    public boolean isValid() {
        return (id != null) || (bookDto != null);
    }

}
