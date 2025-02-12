package org.hl.wirtualnyregalbackend.bookshelf.model.dto;

import org.hl.wirtualnyregalbackend.bookshelf.model.BookshelfType;

public record BookshelfHeaderResponse(Long id,
                                      String name,
                                      BookshelfType type) {
}
