package org.hl.wirtualnyregalbackend.reading_book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.book.BookCommandService;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfQueryService;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.reading_book.dto.BookWithIdDto;
import org.hl.wirtualnyregalbackend.reading_book.dto.ReadingBookCreateRequest;
import org.hl.wirtualnyregalbackend.reading_book.dto.ReadingBookResponse;
import org.hl.wirtualnyregalbackend.reading_book.dto.ReadingBookUpdateRequest;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookChangedStatusEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_note.ReadingNoteCommandService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Clock;
import java.time.Instant;
import java.util.Locale;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class ReadingBookCommandService {

    private final ReadingBookRepository repository;
    private final ReadingBookQueryService readingBookQuery;
    private final BookshelfQueryService bookshelfQuery;
    private final BookCommandService bookCommand;
    private final ReadingNoteCommandService noteCommand;
    private final ApplicationEventPublisher eventPublisher;
    private final Clock clock;


    @Transactional
    public ReadingBookResponse createReadingBook(ReadingBookCreateRequest readingBookRequest, MultipartFile bookCover) {
        Bookshelf bookshelf = bookshelfQuery.findBookshelfById(readingBookRequest.bookshelfId());
        BookWithIdDto bookWithIdDto = readingBookRequest.book();
        Book book = bookCommand.findOrCreateBook(bookWithIdDto.getId(), bookWithIdDto.getBookRequest(), bookCover);
        ReadingBook readingBook = ReadingBookMapper.toReadingBook(readingBookRequest, bookshelf, book);
        eventPublisher.publishEvent(ReadingBookCreatedEvent.from(readingBook));
        log.info("Created Reading Book: {}", readingBook);
        Locale locale = LocaleContextHolder.getLocale();
        return ReadingBookMapper.toReadingBookResponse(readingBook, locale);
    }

    @Transactional
    public ReadingBookResponse updateReadingBook(Long readingBookId, ReadingBookUpdateRequest readingBookRequest) {
        ReadingBook readingBook = readingBookQuery.findReadingBookById(readingBookId);
        log.info("Updating Reading Book: {} by request: {}", readingBook, readingBookRequest);

        ReadingStatus status = readingBookRequest.status();
        if (status != null) {
            ReadingBookDurationRange rbdr = ReadingBookDurationRange.merge(readingBook.getDurationRange(), readingBookRequest.durationRange());
            readingBook.changeStatus(status, rbdr);
        }

        Integer currentPage = readingBookRequest.currentPage();
        if (currentPage != null) {
            readingBook.changeCurrentPage(currentPage);
        }

        log.info("Updated Reading Book: {}", readingBook);
        Locale locale = LocaleContextHolder.getLocale();
        return ReadingBookMapper.toReadingBookResponse(readingBook, locale);
    }

    public ReadingBookResponse moveReadingBook(Long readingBookId, Long bookshelfId) {
        Bookshelf bookshelf = bookshelfQuery.findBookshelfById(bookshelfId);
        ReadingBook readingBook = readingBookQuery.findReadingBookById(readingBookId);
        log.info("Moving Reading Book: {} to bookshelf: {}", readingBook, bookshelf);
        readingBook.setBookshelf(bookshelf);
        repository.save(readingBook);
        Locale locale = LocaleContextHolder.getLocale();
        return ReadingBookMapper.toReadingBookResponse(readingBook, locale);
    }

    @Transactional
    public ReadingBookResponse changeReadingBookStatus(Long readingBookId, ReadingStatus status) {
        ReadingBook book = readingBookQuery.findReadingBookById(readingBookId);
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


    @Transactional
    public void deleteReadingBook(Long readingBookId) {
        ReadingBook readingBook = readingBookQuery.findReadingBookById(readingBookId);
        eventPublisher.publishEvent(ReadingBookDeletedEvent.from(readingBook));

        noteCommand.deleteNotesByReadingBookId(readingBookId);
        repository.delete(readingBook);
        log.info("Deleted Reading Book: {}", readingBook);
    }

}
