package org.hl.wirtualnyregalbackend.reading_book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReadingBookHelper {

    private final ReadingBookRepository readingBookRepository;

    public Long getTotalBooks(Long bookshelfId) {
        return readingBookRepository.countBooks(bookshelfId);
    }

}
