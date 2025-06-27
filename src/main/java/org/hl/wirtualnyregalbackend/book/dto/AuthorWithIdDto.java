package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class AuthorWithIdDto {

    private Long id;

    @JsonUnwrapped
    @Valid
    private AuthorMutationDto authorDto;

    @JsonIgnore
    @AssertTrue(message = "Provide either author ID or author details.")
    public boolean isValid() {
        return (id != null) && (authorDto != null);
    }

}
