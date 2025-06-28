package org.hl.wirtualnyregalbackend.bookshelf_book.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookshelfBookWithBookshelfId extends BookshelfBookMutationDto {

    private Long bookshelfId;

}
