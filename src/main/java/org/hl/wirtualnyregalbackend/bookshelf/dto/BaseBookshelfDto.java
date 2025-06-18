package org.hl.wirtualnyregalbackend.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
abstract class BaseBookshelfDto {

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    protected final String name;

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    protected final BookshelfType type;

    @JsonProperty
    @StringConstraints
    protected final String description;

}
