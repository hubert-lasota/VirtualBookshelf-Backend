package org.hl.wirtualnyregalbackend.book.model.dto.request;

import java.time.LocalDate;
import java.util.Collection;

public record BookRequest(String isbn,
                          String title,
                          Collection<String> authors,
                          Collection<String> genres,
                          Collection<String> publishers,
                          LocalDate publishedAt,
                          Integer publishedYear,
                          Integer numberOfPages,
                          String languageTag,
                          String description)  {
}
