package org.hl.wirtualnyregalbackend.infrastructure.recommendation;

import org.hl.wirtualnyregalbackend.application.recommendation.BookGenreRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class JpaBookGenreRecommendationRepository implements BookGenreRecommendationRepository {

    private final SpringJpaBookGenreRecommendationRepository recommendationRepository;

    JpaBookGenreRecommendationRepository(SpringJpaBookGenreRecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public BookGenreRecommendation save(BookGenreRecommendation bookGenreRecommendation) {
        return recommendationRepository.save(bookGenreRecommendation);
    }

    @Override
    public List<Long> findNonExistingBookGenreIds(List<Long> bookGenreIds, Long userId) {
        return recommendationRepository.findNonExistingBookGenreIds(bookGenreIds, userId);
    }
}

@Repository
interface SpringJpaBookGenreRecommendationRepository extends JpaRepository<BookGenreRecommendation, Long> {

    @Query("""
        select bg.id 
        from BookGenreRecommendation bgr 
        join BookGenre bg 
        where bgr.user.id=:userId and bg.id not in :bookGenreIds
    """)
    List<Long> findNonExistingBookGenreIds(List<Long> bookGenreIds, Long userId);
}
