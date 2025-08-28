package org.hl.wirtualnyregalbackend.reading_book;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.exception.ReadingBookNotFoundException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class ReadingBookHelper {

    private final ReadingBookRepository readingBookRepository;

    public ReadingBook findReadingBookById(Long readingBookId) throws ReadingBookNotFoundException {
        Optional<ReadingBook> bookOpt = readingBookId != null ? readingBookRepository.findById(readingBookId) : Optional.empty();
        return bookOpt.orElseThrow(() -> {
            log.warn("ReadingBook not found with ID: {}", readingBookId);
            return new ReadingBookNotFoundException(readingBookId);
        });
    }

    @Nullable
    public ReadingBook findUserReadingBookByBookId(Long bookId, User user) {
        return readingBookRepository.findByBookIdAndUserId(bookId, user.getId()).orElse(null);
    }

}
