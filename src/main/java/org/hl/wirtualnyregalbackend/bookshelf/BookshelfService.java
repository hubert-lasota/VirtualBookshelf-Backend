package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.book.dao.BookRepository;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.dao.BookshelfRepository;
import org.hl.wirtualnyregalbackend.bookshelf.model.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.model.BookshelfType;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfResponse;
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
    private final BookRepository bookRepository;

    public BookshelfService(BookshelfRepository bookshelfRepository, BookRepository bookRepository) {
        this.bookshelfRepository = bookshelfRepository;
        this.bookRepository = bookRepository;
    }


    public BookshelfResponseDto createBookshelf(BookshelfDto bookshelfDto, User user) {
        Bookshelf bookshelf = BookshelfMapper.toBookshelf(bookshelfDto, user);
        bookshelfRepository.save(bookshelf);
        return BookshelfMapper.toBookshelfResponseDto(bookshelf);
    }

    public void addDefaultBookshelvesToUser(User user) {
        List<Bookshelf> bookshelvesToSave = new ArrayList<>();
        Locale locale = LocaleContextHolder.getLocale();
        String toReadName;
        String readingName;
        String readName;
        if(locale.toLanguageTag().equals("pl-PL")) {
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

    public void addBookToBookshelf(Long bookshelfId, Long bookId) {
        Bookshelf bookshelf = bookshelfRepository.findWithBooksById(bookshelfId);
        Book book = bookRepository.findById(bookId);
        bookshelf.addBook(book);
        bookshelfRepository.save(bookshelf);
    }

    public void removeBookFromBookshelf(Long bookshelfId, Long bookId) {
        Bookshelf bookshelf = bookshelfRepository.findWithBooksById(bookshelfId);
        bookshelf.removeBook(bookId);
        bookshelfRepository.save(bookshelf);
    }

    public List<BookshelfResponse> findUserBookshelves(Long userId) {
        List<Bookshelf> bookshelves = bookshelfRepository.findByUserId(userId);
        Locale locale = LocaleContextHolder.getLocale();
        return bookshelves.stream()
                .map(bookshelf -> BookshelfMapper.toBookshelfResponse(bookshelf, locale))
                .toList();
    }

    public List<Bookshelf> findUserBookshelvesByBookId(Long bookId, User user) {
        return bookshelfRepository.findUserBookshelvesByBookId(bookId, user.getId());
    }

}
