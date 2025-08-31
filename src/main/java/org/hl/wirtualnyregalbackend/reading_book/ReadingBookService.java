package org.hl.wirtualnyregalbackend.reading_book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.dto.BookRequest;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfService;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.reading_book.dto.*;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookChangedStatusEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_note.ReadingNoteHelper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class ReadingBookService {

    private final ReadingBookRepository readingBookRepository;
    private final ReadingBookHelper readingBookHelper;
    private final BookshelfService bookshelfService;
    private final BookService bookService;
    private final ReadingNoteHelper noteHelper;
    private final ApplicationEventPublisher eventPublisher;
    private final Clock clock;

    @Transactional
    public ReadingBookResponse createReadingBook(ReadingBookCreateRequest readingBookRequest, MultipartFile bookCover) {
        Bookshelf bookshelf = bookshelfService.findBookshelfById(readingBookRequest.bookshelfId());
        BookWithIdDto bookWithIdDto = readingBookRequest.book();
        Book book = findOrCreateBook(bookWithIdDto.getId(), bookWithIdDto.getBookRequest(), bookCover);
        ReadingBook readingBook = ReadingBookMapper.toReadingBook(readingBookRequest, bookshelf, book);
        eventPublisher.publishEvent(ReadingBookCreatedEvent.from(readingBook));
        log.info("Created Reading Book: {}", readingBook);
        Locale locale = LocaleContextHolder.getLocale();
        return ReadingBookMapper.toReadingBookResponse(readingBook, locale);
    }

    @Transactional
    public ReadingBookResponse updateReadingBook(Long readingBookId, ReadingBookUpdateRequest readingBookRequest) {
        ReadingBook readingBook = readingBookHelper.findReadingBookById(readingBookId);
        log.info("Updating Reading Book: {} by request: {}", readingBook, readingBookRequest);

        ReadingStatus status = readingBookRequest.status();
        if (status != null) {
            ReadingBookDurationRange rbdr = ReadingBookDurationRange.merge(readingBook.getDurationRange(), readingBookRequest.durationRange());
            readingBook.changeStatus(status, rbdr);
        }

        log.info("Updated Reading Book: {}", readingBook);
        Locale locale = LocaleContextHolder.getLocale();
        return ReadingBookMapper.toReadingBookResponse(readingBook, locale);
    }

    public ReadingBookResponse moveReadingBook(Long readingBookId, Long bookshelfId) {
        Bookshelf bookshelf = bookshelfService.findBookshelfById(bookshelfId);
        ReadingBook readingBook = readingBookHelper.findReadingBookById(readingBookId);
        log.info("Moving Reading Book: {} to bookshelf: {}", readingBook, bookshelf);
        readingBook.setBookshelf(bookshelf);
        readingBookRepository.save(readingBook);
        Locale locale = LocaleContextHolder.getLocale();
        return ReadingBookMapper.toReadingBookResponse(readingBook, locale);
    }

    @Transactional
    public ReadingBookResponse changeReadingBookStatus(Long readingBookId, ReadingStatus status) {
        ReadingBook book = readingBookHelper.findReadingBookById(readingBookId);
        ReadingStatus previousStatus = book.getStatus();
        ReadingBookDurationRange rbdr = null;
        Instant now = Instant.now(clock);
        log.info("Changing Reading Book: {} to status: {}", book, status);
        if (ReadingStatus.READ.equals(status)) {
            rbdr = ReadingBookDurationRange.merge(book.getDurationRange(), ReadingBookDurationRange.of(null, now));
        } else if (ReadingStatus.READING.equals(status)) {
            rbdr = ReadingBookDurationRange.merge(book.getDurationRange(), ReadingBookDurationRange.of(now, null));
        }
        book.changeStatus(status, rbdr);
        eventPublisher.publishEvent(ReadingBookChangedStatusEvent.fromStatusChange(book, previousStatus));
        Locale locale = LocaleContextHolder.getLocale();
        return ReadingBookMapper.toReadingBookResponse(book, locale);
    }

    public ReadingBookListResponse findUserReadingBooks(User user, @Nullable String query) {
        Specification<ReadingBook> spec = ReadingBookSpecification.byUser(user);
        if (query != null) {
            spec = spec.and(ReadingBookSpecification.byQuery(query));
        }
        Locale locale = LocaleContextHolder.getLocale();
        List<ReadingBookResponse> books = readingBookRepository
            .findAll(spec)
            .stream()
            .map((rb) -> ReadingBookMapper.toReadingBookResponse(rb, locale))
            .toList();
        return new ReadingBookListResponse(books);
    }


    @Transactional
    public void deleteReadingBook(Long readingBookId) {
        ReadingBook readingBook = readingBookHelper.findReadingBookById(readingBookId);
        eventPublisher.publishEvent(ReadingBookDeletedEvent.from(readingBook));

        noteHelper.deleteNotesByReadingBookId(readingBookId);
        readingBookRepository.delete(readingBook);
        log.info("Deleted Reading Book: {}", readingBook);
    }

    public boolean isReadingBookAuthor(Long readingBookId, User user) {
        return readingBookRepository.isAuthor(readingBookId, user.getId());
    }


    private Book findOrCreateBook(Long bookId, BookRequest bookDto, MultipartFile cover) {
        return bookService.findBookOptById(bookId)
            .orElseGet(() -> bookService.createBookEntity(bookDto, cover));
    }

}
