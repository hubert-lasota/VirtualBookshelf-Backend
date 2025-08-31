package org.hl.wirtualnyregalbackend.book.dto;

import org.hl.wirtualnyregalbackend.author.dto.AuthorResponse;

import java.util.List;


public record BookResponse(
    Long id,
    String isbn,
    String title,
    List<AuthorResponse> authors,
    String coverUrl,
    Integer pageCount
) {
}