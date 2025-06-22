package org.hl.wirtualnyregalbackend.book_review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;


    public boolean isAuthor(Long bookRatingId, Long userId) {
        return bookReviewRepository.isAuthor(bookRatingId, userId);
    }

}
