package org.hl.wirtualnyregalbackend.book.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;
import org.hl.wirtualnyregalbackend.book_series.model.dto.BookSeriesDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.genre.model.dto.GenreDto;

import java.util.List;

@NotAllFieldsNull(groups = UpdateGroup.class)
public record BookMutationDto(
    @NotEmpty(groups = CreateGroup.class)
    @Valid
    List<BookEditionDto> editions,

    @NotEmpty(groups = CreateGroup.class)
    @Valid
    List<AuthorDto> authors,

    @NotEmpty(groups = CreateGroup.class)
    @Valid
    List<GenreDto> genres,

    @Valid
    List<BookSeriesDto> series,

    @URL
    String coverUrl
) {
}
