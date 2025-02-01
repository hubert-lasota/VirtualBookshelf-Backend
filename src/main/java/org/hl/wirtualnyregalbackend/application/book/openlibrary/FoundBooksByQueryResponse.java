package org.hl.wirtualnyregalbackend.application.book.openlibrary;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.response.BookResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class FoundBooksByQueryResponse {

    @JsonProperty(value = "docs")
    private List<Book> books;

    @JsonProperty(value = "numFound")
    private Integer numFound;

    @JsonProperty(value = "numFoundExact")
    private Boolean numFoundExact;

    @JsonProperty(value = "start")
    private Integer start;

    public Stream<Book> streamBooks() {
        return books == null ? Stream.empty() : books.stream();
    }

    public Integer getNumFound() {
        return numFound;
    }

    static class Book {

        @JsonProperty("key")
        private String rawId;

        @JsonProperty("isbn")
        private List<String> isbn10AndIsbn13;

        @JsonProperty("author_key")
        private List<String> authorsId;

        @JsonProperty("author_name")
        private List<String> authorsFullName;

        @JsonProperty("publisher")
        private List<String> publishers;

        @JsonProperty("title")
        private String title;

        @JsonProperty("publish_year")
        private List<Integer> publishYears;

        @JsonProperty("cover_i")
        private Integer coverId;

        public BookResponse toBookResponse(String openLibraryCoversUrl) {
            String finalId = rawId.replace("/works/", "");
            String finalIsbn = null;
            String isbn1;
            String isbn2;
            if(isbn10AndIsbn13 != null) {
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
                }
            }

            List<AuthorResponse> authorsResponse = new ArrayList<>();
            for(int i = 0; i < authorsId.size(); i++) {
                String id = authorsId.get(i);
                String fullName = authorsFullName.get(i);
                authorsResponse.add(new AuthorResponse(id, fullName, null, null));
            }


            Integer publishedYear = publishYears == null ? null : publishYears.stream()
                    .max(Integer::compareTo)
                    .orElse(null);

            String resolvedCoverUrl = coverId == null ? null : coverId.toString();
            if(resolvedCoverUrl != null) {
                resolvedCoverUrl = openLibraryCoversUrl
                        .replace("{coverId}", resolvedCoverUrl)
                        .replace("{coverSize}", "M");
            }

            return new BookResponse(
                    finalId,
                    finalIsbn,
                    title,
                    authorsResponse,
                    publishers,
                    null,
                    null,
                    null,
                    publishedYear,
                    null,
                    null,
                    resolvedCoverUrl
            );
        }

        public boolean isBook() {
            return (authorsFullName != null && !authorsFullName.isEmpty()) &&
                    (title != null && !title.isBlank());
        }
    }

}





