package org.hl.wirtualnyregalbackend.book.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.ISBN;
import org.hl.wirtualnyregalbackend.common.json.LocaleDeserializer;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.publisher.model.dto.PublisherDto;

import java.util.Collection;
import java.util.Locale;

@NotAllFieldsNull(groups = UpdateGroup.class)
public record BookEditionDto(
    @NotNull(groups = UpdateGroup.class)
    Long id,

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
    Integer numberOfPages,

    @NotEmpty(groups = CreateGroup.class)
    @Valid
    Collection<PublisherDto> publishers,

    @NotNull(groups = CreateGroup.class)
    @Valid
    BookFormatDto format,

    @JsonDeserialize(using = LocaleDeserializer.class)
    @JsonProperty("languageTag")
    Locale language,

    @StringConstraints(allowMultipleSpacesBetweenWords = true)
    String description
) {

    public BookEditionDto {
        if (isbn != null) {
            isbn = isbn.replaceAll("-", "");
        }
    }

}
