package org.hl.wirtualnyregalbackend.application.book.openlibrary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.response.BookResponse;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

class FoundBookByIdResponse {

    @JsonProperty("title")
    private String title;

    @JsonProperty("authors")
    private List<Author> authors;

    @JsonProperty("number_of_pages")
    private Integer numberOfPages;

    @JsonProperty("identifiers")
    private Identifier identifier;

    @JsonProperty("publish_date")
    private String publishedDate;

    @JsonProperty("publishers")
    private List<Publisher> publishers;


    @JsonProperty("cover")
    private Cover cover;

    public BookResponse toBookResponse(String id, String openLibraryBaseUrl) {
        String isbn = identifier.isbn13List.get(0);
        List<AuthorResponse> authorsResponse = authors.stream()
                .map(author -> author.toAuthorResponse(openLibraryBaseUrl))
                .toList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        LocalDate localDate = LocalDate.parse(publishedDate, formatter);
        Integer publishedYear = localDate.getYear();
        Instant publishedAt = localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        String coverUrl = cover.mediumCoverUrl;
        List<String> publisherNames = publishers.stream()
                .map(publisher -> publisher.name)
                .toList();


        return new BookResponse(
                id,
                isbn,
                title,
                authorsResponse,
                publisherNames,
                null,
                null,
                publishedAt,
                publishedYear,
                null,
                numberOfPages,
                coverUrl
        );
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Author {

        @JsonProperty("url")
        private String url;

        @JsonProperty("name")
        private String fullName;

        private AuthorResponse toAuthorResponse(String openLibraryBaseUrl) {
            String id = url.replace(openLibraryBaseUrl + "/authors/", "")
                    .split("/")[0];
            return new AuthorResponse(id, fullName, null, null);
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Publisher {
        String name;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Identifier {

        @JsonProperty("isbn_10")
        List<String> isbn10List;

        @JsonProperty("isbn_13")
        List<String> isbn13List;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Cover {

        @JsonProperty("small")
        private String smallCoverUrl;

        @JsonProperty("medium")
        private String mediumCoverUrl;

        @JsonProperty("large")
        private String largeCoverUrl;

    }

}