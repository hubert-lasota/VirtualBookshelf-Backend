package org.hl.wirtualnyregalbackend.bookshelf;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfListResponse;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponse;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.exception.BookshelfNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class BookshelfQueryService {

    private final BookshelfRepository repository;

    BookshelfListResponse findUserBookshelves(User user) {
        List<BookshelfResponse> bookshelves = repository
            .findByUser(user)
            .stream()
            .map(BookshelfMapper::toBookshelfResponse)
            .toList();
        return new BookshelfListResponse(bookshelves);
    }

    public boolean isUserBookshelfAuthor(Long bookshelfId, Long userId) {
        return repository.isUserBookshelfAuthor(bookshelfId, userId);
    }

    public Bookshelf findBookshelfById(Long bookshelfId) throws BookshelfNotFoundException {
        Optional<Bookshelf> bookshelfOpt = bookshelfId != null ? repository.findById(bookshelfId) : Optional.empty();
        return bookshelfOpt.orElseThrow(() -> {
            log.warn("Bookshelf not found with ID: {}", bookshelfId);
            return new BookshelfNotFoundException(bookshelfId);
        });
    }

}
