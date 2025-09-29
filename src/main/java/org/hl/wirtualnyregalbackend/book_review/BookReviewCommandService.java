package org.hl.wirtualnyregalbackend.book_review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.BookQueryService;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_review.dto.BookReviewCreateRequest;
import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;
import org.hl.wirtualnyregalbackend.book_review.event.BookReviewCreatedOrUpdatedEvent;
import org.hl.wirtualnyregalbackend.book_review.event.BookReviewDeletedEvent;
import org.hl.wirtualnyregalbackend.book_review.exception.BookReviewAlreadyExistsException;
import org.hl.wirtualnyregalbackend.common.review.ReviewMapper;
import org.hl.wirtualnyregalbackend.common.review.ReviewRequest;
import org.hl.wirtualnyregalbackend.common.review.ReviewResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class BookReviewCommandService {

    private final BookReviewRepository repository;
    private final BookReviewQueryService reviewQuery;
    private final BookQueryService bookQuery;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ReviewResponse createBookReview(BookReviewCreateRequest reviewDto, User user) {
        log.info("Creating Book Review: {}", reviewDto);
        Book book = bookQuery.findBookById(reviewDto.getBookId());
        if (repository.existsByBookIdAndUserId(reviewDto.getBookId(), user.getId())) {
            throw new BookReviewAlreadyExistsException("Review already exists for book '%d' and user '%d'".formatted(reviewDto.getBookId(), user.getId()));
        }
        BookReview bookReview = BookReviewMapper.toBookReview(reviewDto, book, user);
        repository.save(bookReview);
        eventPublisher.publishEvent(BookReviewCreatedOrUpdatedEvent.from(bookReview));
        log.info("BookReview created: {}", bookReview);
        return ReviewMapper.toReviewResponse(bookReview);
    }

    @Transactional
    public ReviewResponse updateBookReview(Long bookReviewId, ReviewRequest reviewRequest) {
        log.info("Updating Book Review: {}", reviewRequest);
        BookReview bookReview = reviewQuery.findBookReviewById(bookReviewId);
        Float rating = reviewRequest.getRating();
        if (rating != null) {
            bookReview.setRating(rating);
        }
        String content = reviewRequest.getContent();
        if (content != null) {
            bookReview.setContent(content);
        }
        eventPublisher.publishEvent(BookReviewCreatedOrUpdatedEvent.from(bookReview));
        log.info("BookReview updated: {}", bookReview);
        return ReviewMapper.toReviewResponse(bookReview);
    }

    @Transactional
    public void deleteBookReview(Long bookReviewId) {
        log.info("Deleting Book Review: ID({})", bookReviewId);
        BookReview bookReview = reviewQuery.findBookReviewById(bookReviewId);
        repository.delete(bookReview);
        eventPublisher.publishEvent(BookReviewDeletedEvent.from(bookReview));
        log.info("BookReview deleted: ID({})", bookReviewId);
    }

}
