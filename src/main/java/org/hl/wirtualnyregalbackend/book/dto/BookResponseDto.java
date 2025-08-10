package org.hl.wirtualnyregalbackend.book.dto;

import org.hl.wirtualnyregalbackend.author.dto.AuthorResponseDto;

import java.util.List;


public record BookResponseDto(
    Long id,
    String isbn,
    String title,
    List<AuthorResponseDto> authors,
    String coverUrl
) {
}