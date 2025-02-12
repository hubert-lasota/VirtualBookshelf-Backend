package org.hl.wirtualnyregalbackend.application.book.open_library;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hl.wirtualnyregalbackend.application.book.BookClient;
import org.hl.wirtualnyregalbackend.application.book.exception.BookNotFoundException;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.response.BookResponse;
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

import java.util.List;
import java.util.Locale;

@Component
class OpenLibraryBookClient implements BookClient {

    private static final Logger log = LoggerFactory.getLogger(OpenLibraryBookClient.class);

    private final String OPEN_LIBRARY_BASE_URL = "https://openlibrary.org";
    private final String OPEN_LIBRARY_COVERS_URL = "https://covers.openlibrary.org/b/id/{coverId}-{coverSize}.jpg";
    private final String LANGUAGE_PL = "pol";
    private final String LANGUAGE_EN = "eng";

    @Value("${book.client.id-prefix}")
    private String OPEN_LIBRARY_ID_PREFIX;

    private final String SEARCH_ENDPOINT = "/search.json";
    private final String SEARCH_AUTHORS_ENDPOINT = "/search.json?author={author}";
    private final String FIND_AUTHOR_WORKS_ENDPOINT = "/authors/{authorId}/works.json";
    private final String FIND_BOOK_BY_ISBN_ENDPOINT = "/api/books?bibkeys=ISBN:{isbn}&jscmd=details&format=json";
    private final String FIND_BOOK_BY_ID_ENDPOINT = "/api/books?bibkeys=OLID:{id}&format=json&jscmd=data";

    private final RestClient restClient;


    OpenLibraryBookClient() {
        restClient = RestClient.builder()
                .baseUrl("https://openlibrary.org")
                .defaultHeader("User-Agent", "Virtual Bookshelf (email@example.com)")
                .build();
    }


    @Override
    public Page<BookResponse> searchBooks(String query, Pageable pageable) {
        int size = pageable.getPageSize();
        int page = pageable.getPageNumber() + 1;
        FoundBooksByQueryResponse body;
        try {
            body = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(SEARCH_ENDPOINT)
                            .queryParam("q", query)
                            .queryParam("limit", size)
                            .queryParam("page", page)
                            .build()
                    )
                    .retrieve()
                    .body(FoundBooksByQueryResponse.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Page.empty();
        }

        List<BookResponse> books = body.streamBooks()
                .filter(FoundBooksByQueryResponse.Book::isBook)
                .map(book -> book.toBookResponse(OPEN_LIBRARY_COVERS_URL))
                .toList();

        return new PageImpl<>(books,
                PageRequest.of(page, size),
                body.getNumFound() > 0 ? body.getNumFound() : books.size()
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

            FoundBookByIsbnResponse body = mapper.treeToValue(bookNode, FoundBookByIsbnResponse.class);
            return body.toBook(OPEN_LIBRARY_COVERS_URL, this::resolveLanguage);
        } catch (Exception e) {
            log.error("Error in finding Book by ISBN: {}", e.getMessage());
            throw new BookNotFoundException("Book with isbn:%s not found.".formatted(isbn), e);
        }
    }

    @Override
    public BookResponse findBookById(String id) throws BookNotFoundException {
        BookNotFoundException bookNotFoundException = new BookNotFoundException("Book with id='%s' not found.".formatted(id));
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
            FoundBookByIdResponse body = mapper.treeToValue(bookNode, FoundBookByIdResponse.class);
            return body.toBookResponse(id, OPEN_LIBRARY_BASE_URL);
        } catch (Exception e) {
            log.error("Error in finding Book by ID: {}", e.getMessage());
            throw bookNotFoundException;
        }
    }


    // TODO check the standard of pol, eng and find library to convert it to pl-PL Locale Tags
    private Locale resolveLanguage(String language) {
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

}


