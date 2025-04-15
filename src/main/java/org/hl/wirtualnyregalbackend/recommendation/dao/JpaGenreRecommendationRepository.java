package org.hl.wirtualnyregalbackend.recommendation.dao;

import org.hl.wirtualnyregalbackend.recommendation.model.GenreRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class JpaGenreRecommendationRepository implements GenreRecommendationRepository {

    private final SpringJpaGenreRecommendationRepository recommendationRepository;

    JpaGenreRecommendationRepository(SpringJpaGenreRecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public GenreRecommendation save(GenreRecommendation genreRecommendation) {
        return recommendationRepository.save(genreRecommendation);
    }

    public List<Long> findNonExistingGenreIds(List<Long> genreIds, Long userId) {
        return recommendationRepository.findNonExistingGenreIds(genreIds, userId);
    }
}

@Repository
interface SpringJpaGenreRecommendationRepository extends JpaRepository<GenreRecommendation, Long> {

    @Query("""
        select g.id 
        from GenreRecommendation gr 
        join Genre g 
        where gr.user.id=:userId and g.id not in :genreIds
    """)
    List<Long> findNonExistingGenreIds(List<Long> genreIds, Long userId);
}
