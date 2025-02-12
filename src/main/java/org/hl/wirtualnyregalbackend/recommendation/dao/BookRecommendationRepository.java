package org.hl.wirtualnyregalbackend.recommendation.dao;

import org.hl.wirtualnyregalbackend.recommendation.model.BookRecommendation;

public interface BookRecommendationRepository {

    BookRecommendation save(BookRecommendation bookRecommendation);

    boolean existsByBookIdAndUserId(Long bookId, Long userId);

}
