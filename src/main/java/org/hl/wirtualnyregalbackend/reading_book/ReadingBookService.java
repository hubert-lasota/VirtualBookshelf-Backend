package org.hl.wirtualnyregalbackend.reading_book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.dto.BookRequest;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfService;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.reading_book.dto.*;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookFinishedEvent;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_note.ReadingNoteHelper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ReadingBookService {

    private final ReadingBookRepository readingBookRepository;
    private final ReadingBookHelper readingBookHelper;
    private final BookshelfService bookshelfService;
    private final BookService bookService;
    private final ReadingNoteHelper noteHelper;
    private final ApplicationEventPublisher eventPublisher;

    public ReadingBookResponse createReadingBook(ReadingBookCreateRequest readingBookRequest, MultipartFile bookCover) {
        Bookshelf bookshelf = bookshelfService.findBookshelfById(readingBookRequest.getBookshelfId());
        BookWithIdDto bookWithIdDto = readingBookRequest.getBook();
        Book book = findOrCreateBook(bookWithIdDto.getId(), bookWithIdDto.getBookRequest(), bookCover);
        ReadingBook readingBook = ReadingBookMapper.toReadingBook(readingBookRequest, bookshelf, book);
        readingBookRepository.save(readingBook);
        eventPublisher.publishEvent(new ReadingBookCreatedEvent(readingBook));
        return mapToReadingBookResponseDto(readingBook);
    }

    public ReadingBookResponse updateReadingBook(Long readingBookId, ReadingBookUpdateRequest readingBookRequest) {
        ReadingBook readingBook = readingBookHelper.findReadingBookById(readingBookId);

        ReadingStatus status = readingBookRequest.getStatus();
        if (status != null) {
            readingBook.setStatus(status);
        }

        ReadingBookDurationRange rbdr = ReadingBookDurationRange.merge(readingBook.getDurationRange(), readingBookRequest.getDurationRange());
        readingBook.setDurationRange(rbdr);

        readingBookRepository.save(readingBook);
        return mapToReadingBookResponseDto(readingBook);
    }

    public ReadingBookResponse moveReadingBook(Long readingBookId, Long bookshelfId) {
        Bookshelf bookshelf = bookshelfService.findBookshelfById(bookshelfId);
        ReadingBook readingBook = readingBookHelper.findReadingBookById(readingBookId);
        readingBook.setBookshelf(bookshelf);
        readingBookRepository.save(readingBook);
        return mapToReadingBookResponseDto(readingBook);
    }

    public ReadingBookResponse changeReadingBookStatus(Long readingBookId, ReadingStatus status) {
        ReadingBook book = readingBookHelper.findReadingBookById(readingBookId);
        book.setStatus(status);
        publishReadingBookFinishedEventIfRequired(book, status);
        readingBookRepository.save(book);
        return mapToReadingBookResponseDto(book);
    }

    public ReadingBookListResponse findUserReadingBooks(User user, @Nullable String query) {
        Specification<ReadingBook> spec = ReadingBookSpecification.byUser(user);
        if (query != null) {
            spec = spec.and(ReadingBookSpecification.byQuery(query));
        }

        List<ReadingBookResponse> books = readingBookRepository
            .findAll(spec)
            .stream()
            .map(this::mapToReadingBookResponseDto)
            .toList();
        return new ReadingBookListResponse(books);
    }

    public ReadingBookResponse findReadingBookById(Long readingBookId) {
        ReadingBook book = readingBookHelper.findReadingBookById(readingBookId);
        return mapToReadingBookResponseDto(book);
    }


    @Transactional
    public void deleteReadingBook(Long readingBookId) {
        ReadingBook readingBook = readingBookHelper.findReadingBookById(readingBookId);
        eventPublisher.publishEvent(new ReadingBookDeletedEvent(readingBook));

        noteHelper.deleteNotesByReadingBookId(readingBookId);
        readingBookRepository.delete(readingBook);
    }


    private Book findOrCreateBook(Long bookId, BookRequest bookDto, MultipartFile cover) {
        return bookService.findBookOptById(bookId)
            .orElseGet(() -> bookService.createBookEntity(bookDto, cover));
    }

    private void publishReadingBookFinishedEventIfRequired(ReadingBook readingBook, ReadingStatus status) {
        if (ReadingStatus.READ.equals(status)) {
            eventPublisher.publishEvent(new ReadingBookFinishedEvent(readingBook));
        }
    }

    private ReadingBookResponse mapToReadingBookResponseDto(ReadingBook readingBook) {
        Long totalNotes = noteHelper.getTotalNotes(readingBook.getId());
        // TODO
        return ReadingBookMapper.toReadingBookResponse(readingBook, totalNotes, 0, 0F);
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
