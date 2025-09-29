package org.hl.wirtualnyregalbackend.reading_book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book.model.BookFilter;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.reading_book.dto.ReadingBookListResponse;
import org.hl.wirtualnyregalbackend.reading_book.dto.ReadingBookResponse;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.exception.ReadingBookNotFoundException;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class ReadingBookQueryService {

    private final ReadingBookRepository repository;

    ReadingBookListResponse findUserReadingBooks(User user, BookFilter filter) {
        var spec = ReadingBookSpecification
            .byFilter(filter)
            .and(ReadingBookSpecification.byUser(user));
        Locale locale = LocaleContextHolder.getLocale();
        List<ReadingBookResponse> books = repository
            .findAll(spec)
            .stream()
            .map((rb) -> ReadingBookMapper.toReadingBookResponse(rb, locale))
            .toList();
        return new ReadingBookListResponse(books);
    }

    public ReadingBook findReadingBookById(Long readingBookId) throws ReadingBookNotFoundException {
        Optional<ReadingBook> bookOpt = readingBookId != null ? repository.findById(readingBookId) : Optional.empty();
        return bookOpt.orElseThrow(() -> {
            log.warn("ReadingBook not found with ID: {}", readingBookId);
            return new ReadingBookNotFoundException(readingBookId);
        });
    }

    @Nullable
    public ReadingBook findUserReadingBookByBookId(Long bookId, User user) {
        return repository.findByBookIdAndUserId(bookId, user.getId()).orElse(null);
    }


    public int countUnreadGenres(Set<Genre> genres, User user) {
        List<Long> genreIds = genres
            .stream()
            .map(Genre::getId)
            .toList();
        return repository.countGenresByUserAndReadingStatus(genreIds, user.getId(), ReadingStatus.READ);
    }

    public int countUnreadAuthors(Set<Author> authors, User user) {
        List<Long> authorIds = authors.stream()
            .map(Author::getId)
            .toList();
        return repository.countAuthorsByUserAndReadingStatus(authorIds, user.getId(), ReadingStatus.READ);
    }

    public boolean isReadingBookAuthor(Long readingBookId, User user) {
        return repository.isAuthor(readingBookId, user.getId());
    }

}
