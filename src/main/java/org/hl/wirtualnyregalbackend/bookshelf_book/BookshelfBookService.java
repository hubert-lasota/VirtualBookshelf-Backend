package org.hl.wirtualnyregalbackend.bookshelf_book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.dto.BookMutationDto;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_review.BookReviewService;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfHelper;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.*;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookReadingStatus;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBookNote;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;
import org.hl.wirtualnyregalbackend.common.review.ReviewStats;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookshelfBookService {

    private final BookshelfBookRepository bookshelfBookRepository;
    private final BookshelfHelper bookshelfHelper;
    private final BookService bookService;
    private final BookReviewService bookReviewService;


    public BookshelfBookResponseDto createBookshelfBook(BookshelfBookWithBookshelfId bookshelfBookDto, MultipartFile bookCover) {
        Bookshelf bookshelf = bookshelfHelper.findBookshelfById(bookshelfBookDto.getBookshelfId());
        BookshelfBook bookshelfBook = createBookshelfBookEntity(bookshelfBookDto, bookCover);
        bookshelfBook.setBookshelf(bookshelf);
        bookshelfBookRepository.save(bookshelfBook);
        return mapToBookshelfBookResponseDto(bookshelfBook);
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
        return mapToBookshelfBookResponseDto(bookshelfBook);
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
            List<BookshelfBookNote> notes = noteDtos
                .stream()
                .map(BookshelfBookMapper::toBookshelfBookNote)
                .toList();
            bookshelfBook.setNotes(notes);
        }
    }

    public BookshelfBookResponseDto moveBookshelfBook(Long bookshelfBookId, Long bookshelfId) {
        Bookshelf bookshelf = bookshelfHelper.findBookshelfById(bookshelfId);
        BookshelfBook bookshelfBook = findBookshelfBookEntity(bookshelfBookId);
        bookshelfBook.setBookshelf(bookshelf);
        bookshelfBookRepository.save(bookshelfBook);
        return mapToBookshelfBookResponseDto(bookshelfBook);
    }

    public BookshelfBookResponseDto markBookshelfBookAsRead(Long bookshelfBookId) {
        return modifyBookshelfBook(bookshelfBookId, BookshelfBook::read);
    }

    public BookshelfBookResponseDto markBookshelfBookAsReading(Long bookshelfBookId) {
        return modifyBookshelfBook(bookshelfBookId, BookshelfBook::reading);
    }

    private BookshelfBookResponseDto modifyBookshelfBook(Long bookshelfBookId,
                                                         Consumer<BookshelfBook> callback) {
        BookshelfBook book = findBookshelfBookEntity(bookshelfBookId);
        callback.accept(book);
        bookshelfBookRepository.save(book);
        return mapToBookshelfBookResponseDto(book);
    }


    public BookshelfBookResponseDto findBookshelfBookById(Long bookshelfBookId) {
        BookshelfBook book = findBookshelfBookEntity(bookshelfBookId);
        return mapToBookshelfBookResponseDto(book);
    }

    public void deleteBookshelfBook(Long bookshelfBookId) {
        BookshelfBook bookshelfBook = findBookshelfBookEntity(bookshelfBookId);
        // TOdo add event
        Book book = bookshelfBook.getBook();
        bookshelfBookRepository.delete(bookshelfBook);
    }


    private Book findOrCreateBook(Long bookId, BookMutationDto bookDto, MultipartFile cover) {
        return bookService.findBookOptById(bookId)
            .orElseGet(() -> bookService.createBookEntity(bookDto, cover));
    }

    private BookshelfBook findBookshelfBookEntity(Long bookshelfBookId) throws EntityNotFoundException {
        Optional<BookshelfBook> bookOpt = bookshelfBookId != null ? bookshelfBookRepository.findById(bookshelfBookId) : Optional.empty();
        return bookOpt.orElseThrow(() -> new EntityNotFoundException("BookshelfBook with id: %d not found".formatted(bookshelfBookId)));
    }

    private BookshelfBookResponseDto mapToBookshelfBookResponseDto(BookshelfBook bookshelfBook) {
        ReviewStats stats = bookReviewService.getBookReviewStats(bookshelfBook.getBook().getId());
        Locale locale = LocaleContextHolder.getLocale();
        return BookshelfBookMapper.toBookshelfBookResponseDto(bookshelfBook, stats, locale);
    }

}
