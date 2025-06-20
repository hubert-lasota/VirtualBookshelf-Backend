package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;
import org.hl.wirtualnyregalbackend.common.json.LocaleDeserializer;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

import java.util.List;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
abstract class BaseBookDto {

    @NotNull(groups = CreateGroup.class)
    @ISBN
    protected String isbn;

    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    protected String title;

    @Min(0)
    protected Integer publicationYear;

    @NotNull(groups = CreateGroup.class)
    @Min(0)
    protected Integer pageCount;

    @JsonDeserialize(using = LocaleDeserializer.class)
    @JsonProperty("languageCode")
    protected Locale language;

    @StringConstraints(allowMultipleSpacesBetweenWords = true)
    protected String description;

    @URL
    protected String coverUrl;

    @Valid
    protected PublisherWithIdDto publisher;

    @NotEmpty(groups = CreateGroup.class)
    @Valid
    protected List<AuthorWithIdDto> authors;

    @Valid
    protected List<BookSeriesAssignmentDto> series;


    public void setIsbn(String isbn) {
        this.isbn = isbn != null ? isbn.replaceAll("-", "") : null;
    }

}
