package org.hl.wirtualnyregalbackend.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

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


    protected BaseBookshelfDto(String name,
                               BookshelfType type,
                               String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public BookshelfType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

}
