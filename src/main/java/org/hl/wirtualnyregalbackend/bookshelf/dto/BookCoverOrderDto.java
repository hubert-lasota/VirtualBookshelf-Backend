package org.hl.wirtualnyregalbackend.bookshelf.dto;

public record BookCoverOrderDto(
    Integer bookIndex,
    Integer coverIndex
) {
}
