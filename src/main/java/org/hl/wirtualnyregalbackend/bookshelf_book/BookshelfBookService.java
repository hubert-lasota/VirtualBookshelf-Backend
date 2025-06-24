package org.hl.wirtualnyregalbackend.bookshelf_book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.dto.BookMutationDto;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfHelper;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookWithIdDto;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookReadingStatus;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfBook;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfBookNote;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookNoteDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookWithBookshelfId;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookshelfBookService {

    private final BookshelfBookRepository bookshelfBookRepository;
    private final BookshelfHelper bookshelfHelper;
    private final BookService bookService;


    public BookshelfBookResponseDto createBookshelfBook(BookshelfBookWithBookshelfId bookshelfBookDto, MultipartFile bookCover) {
        Bookshelf bookshelf = bookshelfHelper.findBookshelfById(bookshelfBookDto.getBookshelfId());
        BookshelfBook bookshelfBook = createBookshelfBookEntity(bookshelfBookDto, bookCover);
        bookshelfBook.setBookshelf(bookshelf);
        bookshelfBookRepository.save(bookshelfBook);

        Locale locale = LocaleContextHolder.getLocale();
        return BookshelfBookMapper.toBookshelfBookResponseDto(bookshelfBook, locale);
    }

    public BookshelfBook createBookshelfBookEntity(BookshelfBookMutationDto bookshelfBookDto, MultipartFile bookCover) {
        BookWithIdDto bookWithIdDto = bookshelfBookDto.getBook();
        // TOdo add event
        Book book = findOrCreateBook(bookWithIdDto.getId(), bookWithIdDto.getBookDto(), bookCover);
        return BookshelfBookMapper.toBookshelfBook(bookshelfBookDto, book);
    }

    public BookshelfBookResponseDto updateBookshelfBook(Long bookshelfBookId, BookshelfBookWithBookshelfId bookshelfBookDto) {
        Bookshelf bookshelf = bookshelfHelper.findBookshelfById(bookshelfBookDto.getBookshelfId());
        BookshelfBook bookshelfBook = bookshelf.getBookshelfBookById(bookshelfBookId);
        updateBookshelfBook(bookshelfBook, bookshelfBookDto);
        bookshelfBookRepository.save(bookshelfBook);

        Locale locale = LocaleContextHolder.getLocale();
        return BookshelfBookMapper.toBookshelfBookResponseDto(bookshelfBook, locale);
    }

    public void updateBookshelfBook(BookshelfBook bookshelfBook, BookshelfBookMutationDto bookshelfBookDto) {
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
                .map(BookshelfBookMapper::toBookshelfBookNote)
                .toList();
            bookshelfBook.setNotes(notes);
        }
    }

    public BookshelfBookResponseDto findBookshelfBookById(Long bookshelfBookId) {
        BookshelfBook book = findBookshelfBook(bookshelfBookId);

        Locale locale = LocaleContextHolder.getLocale();
        return BookshelfBookMapper.toBookshelfBookResponseDto(book, locale);
    }

    public void deleteBookshelfBook(Long bookshelfBookId) {
        BookshelfBook bookshelfBook = findBookshelfBook(bookshelfBookId);
        // TOdo add event
        Book book = bookshelfBook.getBook();
        bookshelfBookRepository.delete(bookshelfBook);
    }


    private Book findOrCreateBook(Long bookId, BookMutationDto bookDto, MultipartFile cover) {
        return bookService.findBookOptById(bookId)
            .orElseGet(() -> bookService.createBookEntity(bookDto, cover));
    }

    private BookshelfBook findBookshelfBook(Long bookshelfBookId) throws EntityNotFoundException {
        Optional<BookshelfBook> bookOpt = bookshelfBookId != null ? bookshelfBookRepository.findById(bookshelfBookId) : Optional.empty();
        return bookOpt.orElseThrow(() -> new EntityNotFoundException("BookshelfBook with id: %d not found".formatted(bookshelfBookId)));
    }

}
