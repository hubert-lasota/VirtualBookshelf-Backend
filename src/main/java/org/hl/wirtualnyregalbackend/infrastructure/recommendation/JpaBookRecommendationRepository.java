package org.hl.wirtualnyregalbackend.infrastructure.recommendation;

import org.hl.wirtualnyregalbackend.application.recommendation.BookRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaBookRecommendationRepository implements BookRecommendationRepository {

    private final SpringJpaBookGenreRecommendationRepository recommendationRepository;

    public JpaBookRecommendationRepository(SpringJpaBookGenreRecommendationRepository recommendationRepository) {
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
