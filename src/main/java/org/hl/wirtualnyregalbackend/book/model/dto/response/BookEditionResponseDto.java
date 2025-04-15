package org.hl.wirtualnyregalbackend.book.model.dto.response;

import org.hl.wirtualnyregalbackend.book.model.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.publisher.model.dto.PublisherDto;

import java.util.Collection;

public record BookEditionResponseDto(
    Long id,
    String isbn,
    String title,
    Integer publicationYear,
    Integer numberOfPages,
    Collection<PublisherDto> publishers,
    BookFormatDto format
) {
}
