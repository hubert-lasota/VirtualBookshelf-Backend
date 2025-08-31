package org.hl.wirtualnyregalbackend.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

public record UserRequest(
    @NotNull(groups = CreateGroup.class)
    @StringConstraints(allowWhitespace = false)
    String username,
    @NotNull(groups = CreateGroup.class)
    @StringConstraints(allowWhitespace = false)
    String password,
    @NotNull(groups = CreateGroup.class)
    @Valid
    UserProfileDto profile
) {
}
