package org.hl.wirtualnyregalbackend.recommendation.dao;

import org.hl.wirtualnyregalbackend.recommendation.model.BookRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaBookRecommendationRepository implements BookRecommendationRepository {

    private final SpringJpaGenreRecommendationRepository recommendationRepository;

    public JpaBookRecommendationRepository(SpringJpaGenreRecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public BookRecommendation save(BookRecommendation bookRecommendation) {
        return null;
    }

    @Override
    public boolean existsByBookIdAndUserId(Long bookId, Long userId) {
        return false;
    }
}

@Repository
interface SpringJpaBookRecommendationRepository extends JpaRepository<BookRecommendation, Long> {

}
