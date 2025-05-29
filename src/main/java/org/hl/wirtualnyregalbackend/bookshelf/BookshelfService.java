package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.model.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.model.BookshelfBook;
import org.hl.wirtualnyregalbackend.bookshelf.model.BookshelfType;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfBookMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfBookResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfResponseDto;
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

    BookshelfService(BookshelfRepository bookshelfRepository, BookService bookService) {
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

    public BookshelfBookResponseDto createBookshelfBook(Long bookshelfId,
                                                        BookshelfBookMutationDto bookshelfBookDto,
                                                        User user) {
        Bookshelf bookshelf = findBookshelfById(bookshelfId);
        Book book = bookService.findBookById(bookshelfBookDto.bookId());
        // TOdo add event
        BookshelfBook bookshelfBook = BookshelfMapper.toBookshelfBook(bookshelfBookDto, user, book);
        bookshelf.addBookshelfBook(bookshelfBook);
        bookshelfRepository.saveAndFlush(bookshelf);
        Locale locale = LocaleContextHolder.getLocale();
        return BookshelfMapper.toBookshelfBookResponseDto(bookshelfBook, locale);
    }

    public void deleteBookshelfBook(Long bookshelfId, Long bookshelfBookId) {
        Bookshelf bookshelf = findBookshelfById(bookshelfId);
        // TOdo add event
        bookshelf.removeBookshelfBook(bookshelfBookId);
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

    public boolean isUserBookshelfAuthor(Long bookshelfId, Long userId) {
        return bookshelfRepository.isUserBookshelfAuthor(bookshelfId, userId);
    }

    private Bookshelf findBookshelfById(Long bookshelfId) {
        return bookshelfRepository.findById(bookshelfId)
            .orElseThrow(() -> new IllegalArgumentException("Not found Bookshelf with id = %d".formatted(bookshelfId)));
    }

}
