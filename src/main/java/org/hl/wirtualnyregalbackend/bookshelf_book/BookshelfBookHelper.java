package org.hl.wirtualnyregalbackend.bookshelf_book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookshelfBookHelper {

    private final BookshelfBookRepository bookshelfBookRepository;

    public Long getTotalBooks(Long bookshelfId) {
        return bookshelfBookRepository.countBooks(bookshelfId);
    }

}
