package org.hl.wirtualnyregalbackend.publisher.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublisherMutationDto {

    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    private String name;

}
