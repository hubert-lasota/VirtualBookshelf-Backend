package org.hl.wirtualnyregalbackend.bookshelf;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book_review.BookReviewService;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfListResponse;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfRequest;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponse;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;
import org.hl.wirtualnyregalbackend.bookshelf.exception.BookshelfNotFoundException;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookHelper;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class BookshelfService {

    private final BookshelfRepository bookshelfRepository;
    private final ReadingBookHelper readingBookHelper;
    private final BookReviewService bookReviewService;


    public void addDefaultBookshelvesToUser(User user) {
        List<Bookshelf> bookshelvesToSave = new ArrayList<>();
        Locale locale = LocaleContextHolder.getLocale();
        String toReadName;
        String readingName;
        String readName;
        if (locale.getLanguage().equals("pl")) {
            toReadName = "Do przeczytania";
            readingName = "W trakcie czytania";
            readName = "Przeczytane";
        } else {
            toReadName = "To read";
            readingName = "Reading";
            readName = "Read";
        }
        bookshelvesToSave.add(new Bookshelf(toReadName, BookshelfType.TO_READ, "", user));
        bookshelvesToSave.add(new Bookshelf(readingName, BookshelfType.READING, "", user));
        bookshelvesToSave.add(new Bookshelf(readName, BookshelfType.READ, "", user));
        bookshelfRepository.saveAll(bookshelvesToSave);
    }


    public BookshelfResponse createBookshelf(BookshelfRequest bookshelfRequest, User user) {
        Bookshelf bookshelf = BookshelfMapper.toBookshelf(bookshelfRequest, user);
        bookshelfRepository.save(bookshelf);
        return BookshelfMapper.toBookshelfResponse(bookshelf);
    }


    public BookshelfResponse updateBookshelf(Long id, BookshelfRequest bookshelfRequest) {
        Bookshelf bookshelf = findBookshelfById(id);

        String name = bookshelfRequest.name();
        if (name != null) {
            bookshelf.setName(name);
        }

        BookshelfType type = bookshelfRequest.type();
        if (type != null) {
            bookshelf.setType(type);
        }

        String description = bookshelfRequest.description();
        if (description != null) {
            bookshelf.setDescription(description);
        }

        bookshelfRepository.save(bookshelf);
        return BookshelfMapper.toBookshelfResponse(bookshelf);
    }

    public void deleteBookshelf(Long id) {
        bookshelfRepository.deleteById(id);
    }

    public BookshelfListResponse findUserBookshelves(Long userId) {
        List<BookshelfResponse> bookshelves = bookshelfRepository
            .findByUserId(userId)
            .stream()
            .map(BookshelfMapper::toBookshelfResponse)
            .toList();
        return new BookshelfListResponse(bookshelves);
    }

    public boolean isUserBookshelfAuthor(Long bookshelfId, Long userId) {
        return bookshelfRepository.isUserBookshelfAuthor(bookshelfId, userId);
    }

    public Bookshelf findBookshelfById(Long bookshelfId) throws BookshelfNotFoundException {
        Optional<Bookshelf> bookshelfOpt = bookshelfId != null ? bookshelfRepository.findById(bookshelfId) : Optional.empty();
        return bookshelfOpt.orElseThrow(() -> new BookshelfNotFoundException(bookshelfId));
    }

}
