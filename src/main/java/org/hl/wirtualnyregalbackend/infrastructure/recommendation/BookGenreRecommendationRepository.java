package org.hl.wirtualnyregalbackend.infrastructure.recommendation;

import org.hl.wirtualnyregalbackend.application.recommendation.BookGenreRecommendation;

import java.util.List;


public interface BookGenreRecommendationRepository {

    BookGenreRecommendation save(BookGenreRecommendation bookGenreRecommendation);

    List<Long> findNonExistingBookGenreIds(List<Long> bookGenreIds, Long userId);

}
