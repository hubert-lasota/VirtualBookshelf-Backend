package org.hl.wirtualnyregalbackend.bookshelf.dto;

import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;

import java.util.List;

@NotAllFieldsNull
public class BookshelfUpdateDto extends BaseBookshelfDto {

    private final List<BookshelfBookUpdateDto> books;

    public BookshelfUpdateDto(String name,
                              BookshelfType type,
                              String description,
                              List<BookshelfBookUpdateDto> books) {
        super(name, type, description);
        this.books = books;
    }

    public List<BookshelfBookUpdateDto> getBooks() {
        return books;
    }

}
