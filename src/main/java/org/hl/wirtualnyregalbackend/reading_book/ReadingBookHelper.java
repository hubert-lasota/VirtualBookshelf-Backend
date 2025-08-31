package org.hl.wirtualnyregalbackend.reading_book;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.exception.ReadingBookNotFoundException;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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


    public int countUnreadGenres(Set<Genre> genres, User user) {
        List<Long> genreIds = genres
            .stream()
            .map(Genre::getId)
            .toList();
        return readingBookRepository.countGenresByUserAndReadingStatus(genreIds, user.getId(), ReadingStatus.READ);
    }

    public int countUnreadAuthors(Set<Author> authors, User user) {
        List<Long> authorIds = authors.stream()
            .map(Author::getId)
            .toList();
        return readingBookRepository.countAuthorsByUserAndReadingStatus(authorIds, user.getId(), ReadingStatus.READ);
    }

}
