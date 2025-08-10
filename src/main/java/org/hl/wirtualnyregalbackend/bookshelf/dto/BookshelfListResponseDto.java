package org.hl.wirtualnyregalbackend.bookshelf.dto;

import java.util.List;

public record BookshelfListResponseDto(List<BookshelfResponseDto> bookshelves) {
}
