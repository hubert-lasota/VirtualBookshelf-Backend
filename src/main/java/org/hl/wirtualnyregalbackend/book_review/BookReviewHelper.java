package org.hl.wirtualnyregalbackend.book_review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookReviewHelper {

    private final BookReviewRepository bookReviewRepository;

    @Nullable
    public BookReview findBookReviewByBookIdAndUserId(Long bookReviewId, Long userId) {
        return bookReviewRepository.findByBookIdAndUserId(bookReviewId, userId).orElse(null);
    }

    public Double calculateBookRatingAverage(Book book) {
        Double avg = bookReviewRepository.calculateAverageRatingByBook(book);
        return avg == null ? 0.0D : avg;
    }

}
