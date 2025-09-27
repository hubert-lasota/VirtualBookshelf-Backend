package org.hl.wirtualnyregalbackend.book_review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_review.dto.BookReviewCreateRequest;
import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;
import org.hl.wirtualnyregalbackend.book_review.event.BookReviewCreatedOrUpdatedEvent;
import org.hl.wirtualnyregalbackend.book_review.event.BookReviewDeletedEvent;
import org.hl.wirtualnyregalbackend.book_review.exception.BookReviewAlreadyExistsException;
import org.hl.wirtualnyregalbackend.book_review.exception.BookReviewNotFoundException;
import org.hl.wirtualnyregalbackend.common.review.ReviewMapper;
import org.hl.wirtualnyregalbackend.common.review.ReviewPageResponse;
import org.hl.wirtualnyregalbackend.common.review.ReviewRequest;
import org.hl.wirtualnyregalbackend.common.review.ReviewResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookService bookService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ReviewResponse createBookReview(BookReviewCreateRequest reviewDto, User user) {
        Book book = bookService.findBookById(reviewDto.getBookId());
        if (bookReviewRepository.existsByBookIdAndUserId(reviewDto.getBookId(), user.getId())) {
            throw new BookReviewAlreadyExistsException("Review already exists for book '%d' and user '%d'".formatted(reviewDto.getBookId(), user.getId()));
        }
        BookReview bookReview = BookReviewMapper.toBookReview(reviewDto, book, user);
        bookReviewRepository.save(bookReview);
        eventPublisher.publishEvent(BookReviewCreatedOrUpdatedEvent.from(bookReview));
        return ReviewMapper.toReviewResponse(bookReview);
    }

    @Transactional
    public ReviewResponse updateBookReview(Long bookReviewId, ReviewRequest reviewRequest) {
        BookReview bookReview = findBookReviewById(bookReviewId);
        Float rating = reviewRequest.getRating();
        if (rating != null) {
            bookReview.setRating(rating);
        }
        String content = reviewRequest.getContent();
        if (content != null) {
            bookReview.setContent(content);
        }
        eventPublisher.publishEvent(BookReviewCreatedOrUpdatedEvent.from(bookReview));
        return ReviewMapper.toReviewResponse(bookReview);
    }

    @Transactional
    public void deleteBookReview(Long bookReviewId) {
        BookReview bookReview = findBookReviewById(bookReviewId);
        bookReviewRepository.delete(bookReview);
        eventPublisher.publishEvent(BookReviewDeletedEvent.from(bookReview));
    }

    public ReviewPageResponse findBookReviews(Long bookId, Pageable pageable) {
        Page<ReviewResponse> page = bookReviewRepository
            .findByBookId(bookId, pageable)
            .map(ReviewMapper::toReviewResponse);
        return ReviewPageResponse.from(page);
    }

    public boolean isBookReviewAuthor(Long bookReviewId, User user) {
        return bookReviewRepository.isAuthor(bookReviewId, user.getId());
    }

    private BookReview findBookReviewById(Long id) throws BookReviewNotFoundException {
        Optional<BookReview> reviewOpt = id != null ? bookReviewRepository.findById(id) : Optional.empty();
        return reviewOpt.orElseThrow(() -> new BookReviewNotFoundException(id));
    }

}
