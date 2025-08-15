package org.hl.wirtualnyregalbackend.reading_book;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ReadingBookHelper {

    private final ReadingBookRepository readingBookRepository;

    public Long getTotalBooks(Long bookshelfId) {
        return readingBookRepository.countBooks(bookshelfId);
    }

    public ReadingBook findReadingBookEntityById(Long readingBookId) throws EntityNotFoundException {
        Optional<ReadingBook> bookOpt = readingBookId != null ? readingBookRepository.findById(readingBookId) : Optional.empty();
        return bookOpt.orElseThrow(() -> new EntityNotFoundException("ReadingBook with id: %d not found".formatted(readingBookId)));
    }

    @Nullable
    public ReadingBook findUserReadingBookByBookId(Long bookId, User user) {
        return readingBookRepository.findByBookIdAndUserId(bookId, user.getId()).orElse(null);
    }

}
