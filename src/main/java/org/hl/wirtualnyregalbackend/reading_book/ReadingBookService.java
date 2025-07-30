package org.hl.wirtualnyregalbackend.reading_book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.dto.BookMutationDto;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_review.BookReviewService;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfService;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.common.review.ReviewStats;
import org.hl.wirtualnyregalbackend.reading_book.dto.BookWithIdDto;
import org.hl.wirtualnyregalbackend.reading_book.dto.ReadingBookCreateDto;
import org.hl.wirtualnyregalbackend.reading_book.dto.ReadingBookMutationDto;
import org.hl.wirtualnyregalbackend.reading_book.dto.ReadingBookResponseDto;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookFinishedEvent;
import org.hl.wirtualnyregalbackend.reading_note.ReadingNoteHelper;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ReadingBookService {

    private final ReadingBookRepository readingBookRepository;
    private final ReadingBookHelper readingBookHelper;
    private final BookshelfService bookshelfService;
    private final BookService bookService;
    private final BookReviewService bookReviewService;
    private final ReadingNoteHelper noteHelper;
    private final ApplicationEventPublisher eventPublisher;

    public ReadingBookResponseDto createReadingBook(ReadingBookCreateDto readingBookDto, MultipartFile bookCover) {
        Bookshelf bookshelf = bookshelfService.findBookshelfById(readingBookDto.getBookshelfId());
        BookWithIdDto bookWithIdDto = readingBookDto.getBook();
        Book book = findOrCreateBook(bookWithIdDto.getId(), bookWithIdDto.getBookDto(), bookCover);
        ReadingBook readingBook = ReadingBookMapper.toReadingBook(readingBookDto, bookshelf, book);
        readingBookRepository.save(readingBook);
        eventPublisher.publishEvent(new ReadingBookCreatedEvent(readingBook));
        return mapToReadingBookResponseDto(readingBook);
    }

    public ReadingBookResponseDto updateReadingBook(Long readingBookId, ReadingBookMutationDto readingBookDto) {
        ReadingBook readingBook = readingBookHelper.findReadingBookEntityId(readingBookId);

        ReadingStatus status = readingBookDto.getStatus();
        if (status != null) {
            readingBook.setStatus(status);
        }

        Instant startedReadingAt = readingBookDto.getStartedReadingAt() != null
            ? readingBookDto.getStartedReadingAt()
            : readingBook.getStartedReadingAt();

        Instant finishedReadingAt = readingBookDto.getFinishedReadingAt() != null
            ? readingBookDto.getFinishedReadingAt()
            : readingBook.getFinishedReadingAt();

        readingBook.setReadingPeriod(startedReadingAt, finishedReadingAt);

        readingBookRepository.save(readingBook);
        return mapToReadingBookResponseDto(readingBook);
    }

    public ReadingBookResponseDto moveReadingBook(Long readingBookId, Long bookshelfId) {
        Bookshelf bookshelf = bookshelfService.findBookshelfById(bookshelfId);
        ReadingBook readingBook = readingBookHelper.findReadingBookEntityId(readingBookId);
        readingBook.setBookshelf(bookshelf);
        readingBookRepository.save(readingBook);
        return mapToReadingBookResponseDto(readingBook);
    }

    public ReadingBookResponseDto changeReadingBookStatus(Long readingBookId, ReadingStatus status) {
        ReadingBook book = readingBookHelper.findReadingBookEntityId(readingBookId);
        book.setStatus(status);
        publishReadingBookFinishedEventIfRequired(book, status);
        readingBookRepository.save(book);
        return mapToReadingBookResponseDto(book);
    }

    public List<ReadingBookResponseDto> findUserReadingBooks(User user, @Nullable String query) {
        Specification<ReadingBook> spec = ReadingBookSpecification.byUser(user);
        if (query != null) {
            spec = spec.and(ReadingBookSpecification.byQuery(query));
        }

        return readingBookRepository
            .findAll(spec)
            .stream()
            .map(this::mapToReadingBookResponseDto)
            .toList();
    }

    public ReadingBookResponseDto findReadingBookById(Long readingBookId) {
        ReadingBook book = readingBookHelper.findReadingBookEntityId(readingBookId);
        return mapToReadingBookResponseDto(book);
    }


    @Transactional
    public void deleteReadingBook(Long readingBookId) {
        ReadingBook readingBook = readingBookHelper.findReadingBookEntityId(readingBookId);
        eventPublisher.publishEvent(new ReadingBookDeletedEvent(readingBook));

        noteHelper.deleteNotesByReadingBookId(readingBookId);
        readingBookRepository.delete(readingBook);
    }


    private Book findOrCreateBook(Long bookId, BookMutationDto bookDto, MultipartFile cover) {
        return bookService.findBookOptById(bookId)
            .orElseGet(() -> bookService.createBookEntity(bookDto, cover));
    }

    private void publishReadingBookFinishedEventIfRequired(ReadingBook readingBook, ReadingStatus status) {
        if (ReadingStatus.READ.equals(status)) {
            eventPublisher.publishEvent(new ReadingBookFinishedEvent(readingBook));
        }
    }

    private ReadingBookResponseDto mapToReadingBookResponseDto(ReadingBook readingBook) {
        ReviewStats stats = bookReviewService.getBookReviewStats(readingBook.getBook().getId());
        Long totalNotes = noteHelper.getTotalNotes(readingBook.getId());
        Locale locale = LocaleContextHolder.getLocale();
        // TODO
        return ReadingBookMapper.toReadingBookResponseDto(readingBook, stats, totalNotes, 0, 0F, locale);
    }


    private Float calculateProgressPercentage(Integer currentPage, Integer bookPageCount) {
        if (currentPage == null || currentPage <= 0) {
            return 0F;
        }

        if (currentPage >= bookPageCount) {
            return 100F;
        }

        return ((float) currentPage) / bookPageCount * 100F;
    }

}
