package org.hl.wirtualnyregalbackend.bookshelf.dto;

import jakarta.validation.Valid;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;

import java.util.List;

@NotAllFieldsNull(groups = UpdateGroup.class)
public class BookshelfCreateDto extends BaseBookshelfDto {

    @Valid
    protected final List<BookshelfBookCreateDto> books;

    public BookshelfCreateDto(String name,
                              BookshelfType type,
                              String description,
                              List<BookshelfBookCreateDto> books) {
        super(name, type, description);
        this.books = books;
    }

    public List<BookshelfBookCreateDto> getBooks() {
        return books;
    }

}
