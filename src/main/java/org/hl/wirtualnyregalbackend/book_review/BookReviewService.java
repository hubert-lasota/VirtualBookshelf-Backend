package org.hl.wirtualnyregalbackend.book_review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.review.ReviewStats;
import org.hl.wirtualnyregalbackend.common.review.ReviewUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;


    public ReviewStats getBookReviewStats(Long bookId) {
        return getBookReviewStatsByBookIds(List.of(bookId)).get(0);
    }

    public List<ReviewStats> getBookReviewStatsByBookIds(List<Long> bookIds) {
        List<ReviewStats> raws = bookReviewRepository.getReviewStatsByBookIds(bookIds);
        return ReviewUtils.roundAverage(raws);
    }

    public boolean isAuthor(Long bookRatingId, Long userId) {
        return bookReviewRepository.isAuthor(bookRatingId, userId);
    }

}
