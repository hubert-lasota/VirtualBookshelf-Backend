package org.hl.wirtualnyregalbackend.infrastructure.book;

import org.hl.wirtualnyregalbackend.application.book.BookRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRatingRepository {

    BookRating save(BookRating bookRating);

    BookRating findById(Long id);

    void deleteById(Long id);

    boolean isBookRatingAuthor(Long bookRatingId, Long userId);

    boolean existsById(Long id);

    Float getBookRatingAverage(Long bookId);

    Page<BookRating> findRatingPageByBookId(Long bookId, Pageable pageable);

}
