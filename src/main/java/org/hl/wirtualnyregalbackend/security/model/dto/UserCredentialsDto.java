package org.hl.wirtualnyregalbackend.security.model.dto;

import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;

@NotAllFieldsNull(groups = UpdateGroup.class)
public record UserCredentialsDto(
    @StringConstraints(groups = {CreateGroup.class, SignInGroup.class}, allowWhitespace = false)
    String username,

    @StringConstraints(groups = {CreateGroup.class, SignInGroup.class}, allowWhitespace = false)
    String password
) {
}
