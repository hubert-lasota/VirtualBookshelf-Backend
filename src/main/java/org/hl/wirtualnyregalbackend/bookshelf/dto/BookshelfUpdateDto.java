package org.hl.wirtualnyregalbackend.bookshelf.dto;

import lombok.Getter;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;

import java.util.List;

@NotAllFieldsNull
@Getter
public class BookshelfUpdateDto extends BaseBookshelfDto {

    private final List<BookshelfBookUpdateDto> books;

    public BookshelfUpdateDto(String name,
                              BookshelfType type,
                              String description,
                              List<BookshelfBookUpdateDto> books) {
        super(name, type, description);
        this.books = books;
    }

}
