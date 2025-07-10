package org.hl.wirtualnyregalbackend.reading_session.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NotAllFieldsNull
public class ReadingSessionMutationDto {

    @NotNull(groups = CreateGroup.class)
    @Min(1)
    protected Integer pageFrom;
    @Min(1)
    @NotNull(groups = CreateGroup.class)
    protected Integer pageTo;
    @NotNull(groups = CreateGroup.class)
    protected Instant startedReadingAt;
    @NotNull(groups = CreateGroup.class)
    protected Instant finishedReadingAt;

}
