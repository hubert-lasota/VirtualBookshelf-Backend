package org.hl.wirtualnyregalbackend.infrastructure.book;

import org.hl.wirtualnyregalbackend.application.book.BookRating;

public interface BookRatingRepository {

    BookRating save(BookRating bookRating);

    BookRating findById(Long id);

    void deleteById(Long id);

    boolean isBookRatingAuthor(Long bookRatingId, Long userId);

}
