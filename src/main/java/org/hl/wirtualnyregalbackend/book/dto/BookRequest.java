package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;
import org.hl.wirtualnyregalbackend.common.json.LocaleDeserializer;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;

import java.util.List;
import java.util.Locale;

@NotAllFieldsNull(groups = UpdateGroup.class)
public record BookRequest(
    @Min(0)
    Integer publicationYear,

    Long formatId,

    @NotEmpty
    List<Long> genreIds,

    @NotNull(groups = CreateGroup.class)
    @ISBN
    String isbn,
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    String title,
    @NotNull(groups = CreateGroup.class)
    @Min(0)
    Integer pageCount,

    @JsonDeserialize(using = LocaleDeserializer.class)
    @JsonProperty("languageCode")
    Locale language,

    @StringConstraints(allowMultipleSpacesBetweenWords = true)
    String description,

    @URL
    String coverUrl,

    @Valid
    PublisherWithIdDto publisher,

    @NotEmpty(groups = CreateGroup.class)
    @Valid
    List<AuthorWithIdDto> authors
) {

    public BookRequest {
        isbn = isbn != null ? isbn.replaceAll("-", "") : null;
    }

}
