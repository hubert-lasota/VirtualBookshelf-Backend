package org.hl.wirtualnyregalbackend.book.dto;

import org.hl.wirtualnyregalbackend.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;

import java.util.List;


public record BookResponse(
    Long id,
    String isbn,
    String title,
    List<AuthorResponse> authors,
    List<GenreResponse> genres,
    String coverUrl,
    Integer pageCount
) {
}