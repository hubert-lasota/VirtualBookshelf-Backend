package org.hl.wirtualnyregalbackend.book_review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.BookHelper;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_review.dto.BookReviewCreateRequest;
import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.review.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookHelper bookHelper;


    public ReviewResponse createBookReview(BookReviewCreateRequest reviewDto, User user) {
        Book book = bookHelper.findBookById(reviewDto.getBookId());
        if (bookReviewRepository.existsByBookIdAndUserId(reviewDto.getBookId(), user.getId())) {
            throw new InvalidRequestException("Review already exists for book '%d' and user '%d'".formatted(reviewDto.getBookId(), user.getId()));
        }
        BookReview bookReview = BookReviewMapper.toBookReview(reviewDto, book, user);
        bookReviewRepository.save(bookReview);
        return ReviewMapper.toReviewResponse(bookReview);
    }

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
        bookReviewRepository.save(bookReview);
        return ReviewMapper.toReviewResponse(bookReview);
    }

    public void deleteBookReview(Long bookReviewId) {
        BookReview bookReview = findBookReviewById(bookReviewId);
        bookReviewRepository.delete(bookReview);
    }


    public ReviewStatistics getBookReviewStatistics(Long bookId) {
        return bookReviewRepository
            .getReviewStatsByBookId(bookId)
            .orElse(new ReviewStatistics(bookId, 0D, 0L));
    }

    public ReviewPageResponse findBookReviews(Long bookId, Pageable pageable) {
        Page<ReviewResponse> page = bookReviewRepository
            .findByBookId(bookId, pageable)
            .map(ReviewMapper::toReviewResponse);
        return ReviewPageResponse.from(page);
    }

    public boolean isAuthor(Long bookRatingId, Long userId) {
        return bookReviewRepository.isAuthor(bookRatingId, userId);
    }

    private BookReview findBookReviewById(Long id) throws EntityNotFoundException {
        return bookReviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("BookReview not found for id: %d".formatted(id)));
    }

    @Nullable
    public BookReview findBookReviewEntityById(Long id) {
        Optional<BookReview> reviewOpt = id != null ? bookReviewRepository.findById(id) : Optional.empty();
        return reviewOpt.orElse(null);
    }

}
