package org.hl.wirtualnyregalbackend.bookshelf_book_note.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@NotAllFieldsNull
public record BookshelfBookNoteMutationDto(

    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    @Max(50)
    String title,

    @NotNull(groups = CreateGroup.class)
    @StringConstraints(allowNotTrimmed = true)
    String content,

    @NotNull(groups = CreateGroup.class)
    @Min(1)
    Integer startPage,

    @NotNull(groups = CreateGroup.class)
    @Min(1)
    Integer endPage) {

    @AssertTrue(message = "Start page cannot be greater than end page")
    public boolean isValid() {
        if (startPage != null && endPage != null) {
            return startPage <= endPage;
        }
        return true;
    }

}
