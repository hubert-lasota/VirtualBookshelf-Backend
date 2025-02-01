package org.hl.wirtualnyregalbackend.infrastructure.bookshelf.dto;

import org.hl.wirtualnyregalbackend.application.bookshelf.BookshelfType;

public record BookshelfHeaderResponse(Long id,
                                      String name,
                                      BookshelfType type) {
}
