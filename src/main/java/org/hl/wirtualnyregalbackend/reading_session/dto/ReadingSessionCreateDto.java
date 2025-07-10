package org.hl.wirtualnyregalbackend.reading_session.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadingSessionCreateDto extends ReadingSessionMutationDto {

    @NotNull
    private Long readingBookId;

}
