package org.hl.wirtualnyregalbackend.author.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@NotAllFieldsNull
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorMutationDto {
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    private String fullName;

    @NotNull(groups = CreateGroup.class)
    @URL
    private String photoUrl;

    @StringConstraints
    private String description;

}
