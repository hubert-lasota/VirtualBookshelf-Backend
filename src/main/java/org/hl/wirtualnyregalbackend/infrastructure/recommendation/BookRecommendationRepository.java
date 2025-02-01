package org.hl.wirtualnyregalbackend.infrastructure.recommendation;

import org.hl.wirtualnyregalbackend.application.recommendation.BookRecommendation;

public interface BookRecommendationRepository {

    BookRecommendation save(BookRecommendation bookRecommendation);

    boolean existsByBookIdAndUserId(Long bookId, Long userId);

}
