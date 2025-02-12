package org.hl.wirtualnyregalbackend.recommendation;

import org.hl.wirtualnyregalbackend.recommendation.model.BookGenreRecommendation;

import java.util.List;


public interface BookGenreRecommendationRepository {

    BookGenreRecommendation save(BookGenreRecommendation bookGenreRecommendation);

    List<Long> findNonExistingBookGenreIds(List<Long> bookGenreIds, Long userId);

}
