package org.hl.wirtualnyregalbackend.book.model.dto.response;


import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookSeriesDto;
import org.hl.wirtualnyregalbackend.genre.model.dto.GenreDto;

import java.util.Collection;


public record BookResponseDto(
    Long id,
    Collection<BookEditionResponseDto> editions,
    Collection<AuthorDto> authors,
    Collection<GenreDto> genres,
    Collection<BookSeriesDto> series,
    Integer orderInSeries,
    String coverUrl
) { }
