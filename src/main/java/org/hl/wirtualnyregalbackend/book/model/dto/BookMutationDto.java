package org.hl.wirtualnyregalbackend.book.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;
import org.hl.wirtualnyregalbackend.book_series.model.dto.BookSeriesDto;
import org.hl.wirtualnyregalbackend.common.json.LocaleDeserializer;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.genre.model.dto.GenreDto;
import org.hl.wirtualnyregalbackend.publisher.model.dto.PublisherDto;

import java.util.List;
import java.util.Locale;

@NotAllFieldsNull(groups = UpdateGroup.class)
public record BookMutationDto(
    @NotNull(groups = CreateGroup.class)
    @ISBN
    String isbn,

    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    String title,

    @NotNull(groups = CreateGroup.class)
    @Min(0)
    Integer publicationYear,

    @NotNull(groups = CreateGroup.class)
    @Min(0)
    Integer pageCount,


    @NotNull(groups = CreateGroup.class)
    @Valid
    BookFormatDto format,

    @NotNull(groups = CreateGroup.class)
    @Valid
    PublisherDto publisher,

    @JsonDeserialize(using = LocaleDeserializer.class)
    @JsonProperty("languageTag")
    Locale language,

    @StringConstraints(allowMultipleSpacesBetweenWords = true)
    String description,

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

    public BookMutationDto {
        if (isbn != null) {
            isbn = isbn.replaceAll("-", "");
        }
    }

}
