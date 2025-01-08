package org.hl.wirtualnyregalbackend.application.book.openlibrary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hl.wirtualnyregalbackend.application.book.exception.BookClientException;
import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookResponse;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;

class FoundBookByIsbnResponse {

    @JsonProperty("bib_key")
    private String bibKey;

    @JsonProperty("info_url")
    private String infoUrl;

    @JsonProperty("preview")
    private String preview;

    @JsonProperty("preview_url")
    private String previewUrl;

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @JsonProperty("details")
    private BookDetails details;


    public BookResponse toBook(String openLibraryCoverUrl, Function<String, Locale> resolveLanguage)
            throws BookClientException {
        if (details == null) {
            throw new BookClientException("Response has no details.");
        }

        String id = details.id.replace("/books/", "");

        List<AuthorResponse> authorsResponse = streamAuthors()
                .map(Author::toAuthorResponse)
                .toList();

        String isbn;
        String isbn13 = details.isbn13.get(0);
        if(isbn13 == null) {
            String isbn10 = details.isbn10.get(0);
            if(isbn10 == null) {
                throw new BookClientException("Book has no ISBN");
            }
            isbn = isbn10;
        } else {
            isbn = isbn13;
        }
        String coverId = details.covers.get(0).toString();
        String coverUrl = openLibraryCoverUrl
                .replace("coverId", coverId)
                .replace("coverSize", "M");

        Instant publishedAt = null;
        Integer publishedYear = null;
        String publishedDate = details.publishDate;
        if(publishedDate != null) {
            try {
                publishedYear = Integer.parseInt(publishedDate);
            } catch (NumberFormatException e) {
                DateTimeFormatter dateTimeFormatter =
                        DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
                publishedAt = LocalDate.parse(details.publishDate, dateTimeFormatter)
                        .atStartOfDay()
                        .toInstant(ZoneOffset.UTC);
                publishedYear = publishedAt.atZone(ZoneOffset.UTC).getYear();
            }
        }

        String language = details.languages != null ? details.languages.get(0).key : null;
        Locale localeLanguage = null;
        if(language != null) {
            language = language.replace("/languages/", "");
            localeLanguage = resolveLanguage.apply(language);
        }
        return new BookResponse(
                id,
                isbn,
                details.title,
                authorsResponse,
                details.description,
                publishedAt,
                publishedYear,
                localeLanguage == null ? null : localeLanguage.getLanguage(),
                details.numberOfPages,
                coverUrl
        );
    }

    private Stream<Author> streamAuthors() {
        return details.authors == null ? Stream.empty() : details.authors.stream();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class BookDetails {

        @JsonProperty("key")
        private String id;

        @JsonProperty("title")
        private String title;

        @JsonProperty("authors")
        private List<Author> authors;

        @JsonProperty("publish_date")
        private String publishDate;

        @JsonProperty("source_records")
        private List<String> sourceRecords;

        @JsonProperty("number_of_pages")
        private Integer numberOfPages;

        @JsonProperty("publishers")
        private List<String> publishers;

        @JsonProperty("isbn_10")
        private List<String> isbn10;

        @JsonProperty("isbn_13")
        private List<String> isbn13;

        @JsonProperty("description")
        private String description;

        @JsonProperty("languages")
        private List<KeyType> languages;

        @JsonProperty("physical_format")
        private String physicalFormat;

        @JsonProperty("full_title")
        private String fullTitle;

        @JsonProperty("covers")
        private List<Integer> covers;

        @JsonProperty("works")
        private List<Work> works;

        @JsonProperty("key")
        private String key;

        @JsonProperty("latest_revision")
        private Integer latestRevision;

        @JsonProperty("revision")
        private Integer revision;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KeyType {

        @JsonProperty("key")
        private String key;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Author {

        @JsonProperty("key")
        private String key;

        @JsonProperty("name")
        private String name;

        public AuthorResponse toAuthorResponse() {
            return new AuthorResponse(this.key, this.name);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Work {

        @JsonProperty("key")
        private String key;

    }

}