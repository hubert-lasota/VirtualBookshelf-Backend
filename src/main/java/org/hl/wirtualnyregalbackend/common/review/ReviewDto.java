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
public class ReviewDto {
    @Rating
    @NotNull(groups = CreateGroup.class)
    private Float rating;

    @StringConstraints
    private String content;

}
