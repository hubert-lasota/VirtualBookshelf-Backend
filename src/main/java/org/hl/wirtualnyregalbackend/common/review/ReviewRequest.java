package org.hl.wirtualnyregalbackend.common.review;

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
public class ReviewRequest {
    @Rating
    @NotNull(groups = CreateGroup.class)
    protected Float rating;

    @StringConstraints
    protected String content;

}
