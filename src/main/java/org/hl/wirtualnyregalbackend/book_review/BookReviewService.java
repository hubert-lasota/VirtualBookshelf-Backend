package org.hl.wirtualnyregalbackend.book_review;

import org.springframework.stereotype.Service;

@Service
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;

    BookReviewService(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    public boolean isAuthor(Long bookRatingId, Long userId) {
        return bookReviewRepository.isAuthor(bookRatingId, userId);
    }

}
