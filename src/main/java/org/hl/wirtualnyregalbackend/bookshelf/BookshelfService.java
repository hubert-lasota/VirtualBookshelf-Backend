package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.model.entity.BookEdition;
import org.hl.wirtualnyregalbackend.bookshelf.dao.BookshelfRepository;
import org.hl.wirtualnyregalbackend.bookshelf.model.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.model.BookshelfType;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.request.BookshelfMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.response.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class BookshelfService {

    private final BookshelfRepository bookshelfRepository;
    private final BookService bookService;

    public BookshelfService(BookshelfRepository bookshelfRepository, BookService bookService) {
        this.bookshelfRepository = bookshelfRepository;
        this.bookService = bookService;
    }


    public BookshelfResponseDto createBookshelf(BookshelfMutationDto bookshelfMutationDto, User user) {
        Bookshelf bookshelf = BookshelfMapper.toBookshelf(bookshelfMutationDto, user);
        bookshelfRepository.save(bookshelf);
        Locale locale = LocaleContextHolder.getLocale();
        return BookshelfMapper.toBookshelfResponseDto(bookshelf, locale);
    }

    public void addDefaultBookshelvesToUser(User user) {
        List<Bookshelf> bookshelvesToSave = new ArrayList<>();
        Locale locale = LocaleContextHolder.getLocale();
        String toReadName;
        String readingName;
        String readName;
        if (locale.toLanguageTag().equals("pl-PL")) {
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

    public void addBookToBookshelf(Long bookshelfId, Long bookEditionId) {
        Bookshelf bookshelf = bookshelfRepository.findById(bookshelfId);
        BookEdition edition = bookService.findBookEditionEntityById(bookEditionId);
        // TOdo add event
        bookshelf.addBookEdition(edition);
        bookshelfRepository.save(bookshelf);
    }

    public void removeBookFromBookshelf(Long bookshelfId, Long bookEditionId) {
        Bookshelf bookshelf = bookshelfRepository.findById(bookshelfId);
        BookEdition edition = bookService.findBookEditionEntityById(bookEditionId);
        // TOdo add event
        bookshelf.removeBookEdition(edition);
        bookshelfRepository.save(bookshelf);
    }

    public List<BookshelfResponseDto> findUserBookshelves(Long userId) {
        List<Bookshelf> bookshelves = bookshelfRepository.findByUserId(userId);
        Locale locale = LocaleContextHolder.getLocale();
        return bookshelves.stream()
            .map(bookshelf -> BookshelfMapper.toBookshelfResponseDto(bookshelf, locale))
            .toList();
    }

    public List<Bookshelf> findUserBookshelvesByBookId(Long bookId, User user) {
        return bookshelfRepository.findUserBookshelvesByBookId(bookId, user.getId());
    }

}
