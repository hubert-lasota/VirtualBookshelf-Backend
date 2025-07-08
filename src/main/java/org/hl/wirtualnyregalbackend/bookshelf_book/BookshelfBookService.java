package org.hl.wirtualnyregalbackend.bookshelf_book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.dto.BookMutationDto;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_review.BookReviewService;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfService;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookWithIdDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookReadingStatus;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;
import org.hl.wirtualnyregalbackend.bookshelf_book_note.BookshelfBookNoteHelper;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.review.ReviewStats;
import org.hl.wirtualnyregalbackend.common.validation.RangeDateValidator;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookshelfBookService {

    private final BookshelfBookRepository bookshelfBookRepository;
    private final BookshelfService bookshelfService;
    private final BookService bookService;
    private final BookReviewService bookReviewService;
    private final BookshelfBookNoteHelper noteHelper;

    public BookshelfBookResponseDto createBookshelfBook(Long bookshelfId, BookshelfBookMutationDto bookshelfBookDto, MultipartFile bookCover) {
        Bookshelf bookshelf = bookshelfService.findBookshelfById(bookshelfId);
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

    public BookshelfBookResponseDto updateBookshelfBook(Long bookshelfBookId, BookshelfBookMutationDto bookshelfBookDto) {
        BookshelfBook bookshelfBook = findBookshelfBookEntityId(bookshelfBookId);
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

        Instant startedReadingAt = bookshelfBookDto.getStartedReadingAt() != null
            ? bookshelfBookDto.getStartedReadingAt()
            : bookshelfBook.getStartedReadingAt();

        Instant finishedReadingAt = bookshelfBookDto.getFinishedReadingAt() != null
            ? bookshelfBookDto.getFinishedReadingAt()
            : bookshelfBook.getFinishedReadingAt();

        RangeDateValidator.validate(startedReadingAt, finishedReadingAt, "startedReadingAt", "finishedReadingAt");
        bookshelfBook.setStartedReadingAt(startedReadingAt);
        bookshelfBook.setFinishedReadingAt(finishedReadingAt);
    }

    public BookshelfBookResponseDto moveBookshelfBook(Long bookshelfBookId, Long bookshelfId) {
        Bookshelf bookshelf = bookshelfService.findBookshelfById(bookshelfId);
        BookshelfBook bookshelfBook = findBookshelfBookEntityId(bookshelfBookId);
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

    private BookshelfBookResponseDto modifyBookshelfBook(Long bookshelfBookId, Consumer<BookshelfBook> modifier) {
        BookshelfBook book = findBookshelfBookEntityId(bookshelfBookId);
        modifier.accept(book);
        bookshelfBookRepository.save(book);
        return mapToBookshelfBookResponseDto(book);
    }

    public List<BookshelfBookResponseDto> findUserBookshelfBooks(User user, @Nullable String query) {
        Specification<BookshelfBook> spec = BookshelfBookSpecification.byUser(user);
        if (query != null) {
            spec = spec.and(BookshelfBookSpecification.byQuery(query));
        }

        return bookshelfBookRepository
            .findAll(spec)
            .stream()
            .map(this::mapToBookshelfBookResponseDto)
            .toList();
    }

    public BookshelfBookResponseDto findBookshelfBookById(Long bookshelfBookId) {
        BookshelfBook book = findBookshelfBookEntityId(bookshelfBookId);
        return mapToBookshelfBookResponseDto(book);
    }

    public BookshelfBook findBookshelfBookEntityId(Long bookshelfBookId) throws EntityNotFoundException {
        Optional<BookshelfBook> bookOpt = bookshelfBookId != null ? bookshelfBookRepository.findById(bookshelfBookId) : Optional.empty();
        return bookOpt.orElseThrow(() -> new EntityNotFoundException("BookshelfBook with id: %d not found".formatted(bookshelfBookId)));
    }

    @Transactional
    public void deleteBookshelfBook(Long bookshelfBookId) {
        BookshelfBook bookshelfBook = findBookshelfBookEntityId(bookshelfBookId);
        noteHelper.deleteNotesByBookshelfBookId(bookshelfBookId);
        bookshelfBookRepository.delete(bookshelfBook);
        // TOdo add event
        Book book = bookshelfBook.getBook();
    }


    private Book findOrCreateBook(Long bookId, BookMutationDto bookDto, MultipartFile cover) {
        return bookService.findBookOptById(bookId)
            .orElseGet(() -> bookService.createBookEntity(bookDto, cover));
    }


    private BookshelfBookResponseDto mapToBookshelfBookResponseDto(BookshelfBook bookshelfBook) {
        ReviewStats stats = bookReviewService.getBookReviewStats(bookshelfBook.getBook().getId());
        Long totalNotes = noteHelper.getTotalNotes(bookshelfBook.getId());
        Locale locale = LocaleContextHolder.getLocale();
        return BookshelfBookMapper.toBookshelfBookResponseDto(bookshelfBook, stats, totalNotes, locale);
    }

}
