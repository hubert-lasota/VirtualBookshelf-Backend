package org.hl.wirtualnyregalbackend.recommendation.dao;

import org.hl.wirtualnyregalbackend.recommendation.model.GenreRecommendation;

import java.util.List;


public interface GenreRecommendationRepository {

    GenreRecommendation save(GenreRecommendation genreRecommendation);

    List<Long> findNonExistingGenreIds(List<Long> genreIds, Long userId);

}
