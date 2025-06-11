package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.dto.*;
import org.hl.wirtualnyregalbackend.bookshelf.entity.*;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookshelfService {

    private final static Logger log = LoggerFactory.getLogger(BookshelfService.class);

    private final BookshelfRepository bookshelfRepository;
    private final BookService bookService;

    BookshelfService(BookshelfRepository bookshelfRepository, BookService bookService) {
        this.bookshelfRepository = bookshelfRepository;
        this.bookService = bookService;
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
        bookshelvesToSave.add(new Bookshelf(toReadName, BookshelfType.TO_READ, "", user, null));
        bookshelvesToSave.add(new Bookshelf(readingName, BookshelfType.READING, "", user, null));
        bookshelvesToSave.add(new Bookshelf(readName, BookshelfType.READ, "", user, null));
        bookshelfRepository.saveAll(bookshelvesToSave);
    }


    public BookshelfResponseDto createBookshelf(BookshelfCreateDto bookshelfCreateDto, User user) {
        Set<BookshelfBook> bookshelfBook = bookshelfCreateDto.getBooks()
            .stream()
            .map(this::createBookshelfBookEntity)
            .collect(Collectors.toSet());

        Bookshelf bookshelf = BookshelfMapper.toBookshelf(bookshelfCreateDto, user, bookshelfBook);
        bookshelfRepository.save(bookshelf);
        Locale locale = LocaleContextHolder.getLocale();
        return BookshelfMapper.toBookshelfResponseDto(bookshelf, locale);
    }


    public BookshelfResponseDto updateBookshelf(Long id, BookshelfUpdateDto bookshelfDto) {
        Bookshelf bookshelf = findBookshelfById(id);

        String name = bookshelfDto.getName();
        if (name != null) {
            bookshelf.setName(name);
        }

        BookshelfType type = bookshelfDto.getType();
        if (type != null) {
            bookshelf.setType(type);
        }

        String description = bookshelfDto.getDescription();
        if (description != null) {
            bookshelf.setDescription(description);
        }

        List<BookshelfBookUpdateDto> bookDtos = bookshelfDto.getBooks();
        if (bookDtos != null) {
            Set<BookshelfBook> books = bookDtos.stream()
                .map(bookDto -> {
                    Long bookshelfBookId = bookDto.getId();
                    if (bookshelfBookId == null) {
                        return createBookshelfBookEntity(bookDto);
                    }
                    BookshelfBook book = bookshelf.getBookshelfBookById(bookshelfBookId);
                    updateBookshelfBook(book, bookDto);
                    return book;
                })
                .collect(Collectors.toSet());

            bookshelf.setBookshelfBooks(books);
        }

        bookshelfRepository.save(bookshelf);
        Locale locale = LocaleContextHolder.getLocale();
        return BookshelfMapper.toBookshelfResponseDto(bookshelf, locale);
    }

    private void updateBookshelfBook(BookshelfBook bookshelfBook, BookshelfBookUpdateDto bookshelfBookDto) {
        Integer currentPage = bookshelfBookDto.getCurrentPage();
        if (currentPage != null) {
            bookshelfBook.setCurrentPage(currentPage);
        }
        BookReadingStatus status = bookshelfBookDto.getStatus();
        if (status != null) {
            bookshelfBook.setStatus(status);
        }

        RangeDate rangeDate = bookshelfBookDto.getRangeDate();
        if (rangeDate != null) {
            RangeDate merged = RangeDate.merge(bookshelfBook.getRangeDate(), rangeDate);
            bookshelfBook.setRangeDate(merged);
        }

        List<BookshelfBookNoteDto> noteDtos = bookshelfBookDto.getNotes();
        if (noteDtos != null) {
            List<BookshelfBookNote> notes = noteDtos.stream()
                .map(BookshelfMapper::toBookshelfBookNote)
                .toList();
            bookshelfBook.setNotes(notes);
        }
    }

    private BookshelfBook createBookshelfBookEntity(BookshelfBookCreateDto bookshelfBookDto) {
        BookWithIdDto bookWithIdDto = bookshelfBookDto.getBook();
        // TOdo add event
        Book book = bookService.findOrCreateBook(bookWithIdDto.id(), bookWithIdDto.bookDto());
        return BookshelfMapper.toBookshelfBook(bookshelfBookDto, book);
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
            .orElseThrow(() -> new EntityNotFoundException("Not found Bookshelf with id = %d".formatted(bookshelfId)));
    }

}
