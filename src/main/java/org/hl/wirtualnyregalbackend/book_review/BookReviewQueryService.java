package org.hl.wirtualnyregalbackend.book_review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;
import org.hl.wirtualnyregalbackend.book_review.exception.BookReviewNotFoundException;
import org.hl.wirtualnyregalbackend.common.review.ReviewMapper;
import org.hl.wirtualnyregalbackend.common.review.ReviewPageResponse;
import org.hl.wirtualnyregalbackend.common.review.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class BookReviewQueryService {

    private final BookReviewRepository repository;

    public boolean isBookReviewAuthor(Long bookReviewId, User user) {
        return repository.isAuthor(bookReviewId, user.getId());
    }

    @Nullable
    public BookReview findBookReviewByBookIdAndUserId(Long bookReviewId, Long userId) {
        return repository.findByBookIdAndUserId(bookReviewId, userId).orElse(null);
    }

    public Double calculateBookRatingAverage(Book book) {
        Double avg = repository.calculateAverageRatingByBook(book);
        return avg == null ? 0.0D : avg;
    }

    ReviewPageResponse findBookReviews(Long bookId, Pageable pageable) {
        Page<ReviewResponse> page = repository
            .findByBookId(bookId, pageable)
            .map(ReviewMapper::toReviewResponse);
        return ReviewPageResponse.from(page);
    }

    BookReview findBookReviewById(Long id) throws BookReviewNotFoundException {
        Optional<BookReview> reviewOpt = id != null ? repository.findById(id) : Optional.empty();
        return reviewOpt.orElseThrow(() -> {
            log.warn("BookReview not found with ID: {}", id);
            return new BookReviewNotFoundException(id);
        });
    }

}
