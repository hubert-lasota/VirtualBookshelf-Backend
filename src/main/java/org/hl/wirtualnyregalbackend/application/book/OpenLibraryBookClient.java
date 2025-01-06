package org.hl.wirtualnyregalbackend.application.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hl.wirtualnyregalbackend.application.book.exception.BookClientException;
import org.hl.wirtualnyregalbackend.application.book.exception.BookNotFoundException;
import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@Component
class OpenLibraryBookClient implements BookClient {

    private static final Logger log = LoggerFactory.getLogger(OpenLibraryBookClient.class);

    private static final String OPEN_LIBRARY_BASE_URL = "https://openlibrary.org";
    private static final String OPEN_LIBRARY_COVERS_URL = "https://covers.openlibrary.org/b/id/{coverId}-{coverSize}.jpg";
    private static final String LANGUAGE_PL = "pol";
    private static final String LANGUAGE_EN = "eng";

    @Value("${book.client.id-prefix}")
    private String OPEN_LIBRARY_ID_PREFIX;

    private final String SEARCH_ENDPOINT = "/search.json?q={query}";
    private final String SEARCH_AUTHORS_ENDPOINT = "/search.json?author={author}";
    private final String FIND_AUTHOR_WORKS_ENDPOINT = "/authors/{authorId}/works.json";
    private final String FIND_BOOK_BY_ISBN_ENDPOINT = "/api/books?bibkeys=ISBN:{isbn}&jscmd=details&format=json";
    private final String FIND_BOOK_BY_ID_ENDPOINT = "/api/books?bibkeys=OLID:{id}&format=json&jscmd=data";

    private final RestClient restClient;


    OpenLibraryBookClient() {
        restClient = RestClient.builder()
                .baseUrl("https://openlibrary.org")
                .build();
    }


    @Override
    public Page<BookResponse> searchBooks(String query, Pageable pageable) {
        int size = pageable.getPageSize();
        int page = pageable.getPageNumber() + 1;
        OLSearchBooksResponse body;
        try {
            body = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search.json")
                            .queryParam("q", query)
                            .queryParam("limit", size)
                            .queryParam("page", page)
                            .build()
                    )
                    .retrieve()
                    .body(OLSearchBooksResponse.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Page.empty();
        }

        List<BookResponse> books = body.streamBooks()
                .filter(OLSearchBook::isBook)
                .map(OLSearchBook::toBookResponse)
                .toList();

        return new PageImpl<>(books,
                PageRequest.of(page, size),
                body.numFound > 0 ? body.numFound : books.size()
                );
    }

    @Override
    public BookResponse findBookByIsbn(String isbn) throws BookNotFoundException {
        try {
            String rawBody = restClient.get()
                    .uri(FIND_BOOK_BY_ISBN_ENDPOINT.replace("{isbn}", isbn))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(rawBody);
            JsonNode bookNode = rootNode.elements().next();

            OLFoundBookByIsbnResponse body = mapper.treeToValue(bookNode, OLFoundBookByIsbnResponse.class);
            return body.toBook();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BookNotFoundException("Book with isbn:%s not found.".formatted(isbn), e);
        }
    }

    @Override
    public BookResponse findBookById(String id) throws BookNotFoundException {
        BookNotFoundException bookNotFoundException = new BookNotFoundException("Book with id:%s not found.".formatted(id));
        if (id == null || !id.startsWith(OPEN_LIBRARY_ID_PREFIX)) {
            throw bookNotFoundException;
        }

        try {
            String rawBody = restClient.get()
                    .uri(FIND_BOOK_BY_ID_ENDPOINT.replace("{id}", id))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(rawBody);
            JsonNode bookNode = rootNode.elements().next();
            OLFoundBookByIdResponse body = mapper.treeToValue(bookNode, OLFoundBookByIdResponse.class);
            return body.toBookResponse(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw bookNotFoundException;
        }
    }

    private static Locale resolveLanguage(String language) {
        switch (language) {
            case LANGUAGE_PL:
                return Locale.forLanguageTag("pl");
            case LANGUAGE_EN:
                return Locale.forLanguageTag("en");
            default:
                log.info("Language not supported: {}", language);
                return null;
        }
    }

    private static class OLFoundBookByIdResponse {


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

        @JsonProperty("cover")
        private Cover cover;

        private BookResponse toBookResponse(String id) {
            String isbn = identifier.isbn13List.get(0);
            List<AuthorResponse> authorsResponse = authors.stream()
                    .map(Author::toAuthorResponse)
                    .toList();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            LocalDate localDate = LocalDate.parse(publishedDate, formatter);
            Integer publishedYear = localDate.getYear();
            Instant publishedAt = localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
            String coverUrl = cover.mediumCoverUrl;



            return new BookResponse(
                    id,
                    isbn,
                    title,
                    authorsResponse,
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

            private AuthorResponse toAuthorResponse() {
                String id = url.replace(OPEN_LIBRARY_BASE_URL + "/authors/", "")
                        .split("/")[0];
                return new AuthorResponse(id, fullName);
            }

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

    private static class OLSearchBooksResponse {

        @JsonProperty(value = "docs")
        private List<OLSearchBook> books;

        @JsonProperty(value = "numFound")
        private Integer numFound;

        @JsonProperty(value = "numFoundExact")
        private Boolean numFoundExact;

        @JsonProperty(value = "start")
        private Integer start;

        public Stream<OLSearchBook> streamBooks() {
            return books == null ? Stream.empty() : books.stream();
        }

    }

    private static class OLSearchBook {

        @JsonProperty("key")
        private String rawId;

        @JsonProperty("isbn")
        private List<String> isbn10AndIsbn13;

        @JsonProperty("author_key")
        private List<String> authorsId;

        @JsonProperty("author_name")
        private List<String> authorsFullName;

        @JsonProperty("title")
        private String title;

        @JsonProperty("publish_year")
        private List<Integer> publishYears;

        @JsonProperty("cover_i")
        private Integer coverId;

        private BookResponse toBookResponse() {
            String finalId = rawId.replace("/works/", "");
            String finalIsbn;
            String isbn1;
            String isbn2;
            if(isbn10AndIsbn13.size() > 1) {
                isbn1 = isbn10AndIsbn13.get(0);
                isbn2 = isbn10AndIsbn13.get(1);
                if(isbn1.length() > isbn2.length()) {
                    finalIsbn = isbn1;
                } else {
                    finalIsbn = isbn2;
                }
            } else if(isbn10AndIsbn13.size() == 1) {
                finalIsbn = isbn10AndIsbn13.get(0);
            } else {
                throw new BookClientException("Isbn not found");
            }

            List<AuthorResponse> authorsResponse = new ArrayList<>();
            for(int i = 0; i < authorsId.size(); i++) {
                String id = authorsId.get(i);
                String fullName = authorsFullName.get(i);
                authorsResponse.add(new AuthorResponse(id, fullName));
            }


            Integer publishedYear = publishYears == null ? null : publishYears.stream()
                    .max(Integer::compareTo)
                    .orElse(null);

            String resolvedCoverUrl = coverId == null ? null : coverId.toString();
            if(resolvedCoverUrl != null) {
                resolvedCoverUrl = OPEN_LIBRARY_COVERS_URL
                        .replace("{coverId}", resolvedCoverUrl)
                        .replace("{coverSize}", "M");
            }
            return new BookResponse(
                    finalId,
                    finalIsbn,
                    title,
                    authorsResponse,
                    null,
                    null,
                    publishedYear,
                    null,
                    null,
                    resolvedCoverUrl
            );
        }

        public boolean isBook() {
            return isbn10AndIsbn13 != null &&
                    (authorsFullName != null && !authorsFullName.isEmpty()) &&
                    (title != null && !title.isBlank());
        }
    }

    private static class OLFoundBookByIsbnResponse {

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
        private OLFoundBookDetails details;


        private Stream<Author> streamAuthors() {
            return details.authors == null ? Stream.empty() : details.authors.stream();
        }

        private BookResponse toBook() throws BookClientException {
            if (details == null) {
                throw new BookClientException("Response has no details.");
            }

            String id = details.id.replace("/books/", "");

            List<AuthorResponse> authorsResponse = streamAuthors()
                    .map(Author::toAuthorResponse)
                    .toList();

            String isbn = null;
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
            String coverUrl = OPEN_LIBRARY_COVERS_URL
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
                localeLanguage = resolveLanguage(language);
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

        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class OLFoundBookDetails {

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

}


