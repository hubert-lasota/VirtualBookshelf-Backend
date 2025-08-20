package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherRequest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublisherWithIdDto {

    private Long id;

    @JsonUnwrapped
    @Valid
    private PublisherRequest publisherDto;

    @JsonIgnore
    @AssertTrue(message = "Provide either publisher ID or publisher details.")
    public boolean isValid() {
        return (id != null) && (publisherDto != null);
    }

}
