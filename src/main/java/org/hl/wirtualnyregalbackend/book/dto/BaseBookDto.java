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
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

import java.util.List;
import java.util.Locale;

abstract class BaseBookDto {

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    @ISBN
    protected final String isbn;

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    protected final String title;

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    @Min(0)
    protected final Integer publicationYear;

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    @Min(0)
    protected final Integer pageCount;

    @JsonDeserialize(using = LocaleDeserializer.class)
    @JsonProperty("languageCode")
    protected final Locale language;

    @JsonProperty
    @StringConstraints(allowMultipleSpacesBetweenWords = true)
    protected final String description;

    @JsonProperty
    @URL
    protected final String coverUrl;

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    @Valid
    protected final PublisherWithIdDto publisher;

    @JsonProperty
    @NotEmpty(groups = CreateGroup.class)
    @Valid
    protected final List<AuthorWithIdDto> authors;

    @JsonProperty
    @NotEmpty(groups = CreateGroup.class)
    @Valid
    protected final List<GenreWithIdDto> genres;

    @JsonProperty
    @Valid
    protected final List<BookSeriesAssignmentDto> series;

    public BaseBookDto(String isbn,
                       String title,
                       Integer publicationYear,
                       Integer pageCount,
                       Locale language,
                       String description,
                       String coverUrl,
                       PublisherWithIdDto publisher,
                       List<AuthorWithIdDto> authors,
                       List<GenreWithIdDto> genres,
                       List<BookSeriesAssignmentDto> series) {
        this.isbn = isbn != null ? isbn.replaceAll("-", "") : null;
        this.title = title;
        this.publicationYear = publicationYear;
        this.pageCount = pageCount;
        this.language = language;
        this.description = description;
        this.coverUrl = coverUrl;
        this.publisher = publisher;
        this.authors = authors;
        this.genres = genres;
        this.series = series;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public Locale getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public PublisherWithIdDto getPublisher() {
        return publisher;
    }

    public List<AuthorWithIdDto> getAuthors() {
        return authors;
    }

    public List<GenreWithIdDto> getGenres() {
        return genres;
    }

    public List<BookSeriesAssignmentDto> getSeries() {
        return series;
    }

}
