package org.hl.wirtualnyregalbackend.review.book_review.dao;

import org.hl.wirtualnyregalbackend.review.book_review.model.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookReviewRepository {

    BookReview save(BookReview bookReview);

    BookReview findById(Long id);

    void deleteById(Long id);

    boolean isAuthor(Long bookRatingId, Long userId);

    boolean existsById(Long id);

    Float getRatingAverage(Long bookId);

    Page<BookReview> findPageByBookId(Long bookId, Pageable pageable);

}
