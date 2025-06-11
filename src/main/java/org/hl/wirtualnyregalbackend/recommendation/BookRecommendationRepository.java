package org.hl.wirtualnyregalbackend.recommendation;

import org.hl.wirtualnyregalbackend.recommendation.entity.BookRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookRecommendationRepository extends JpaRepository<BookRecommendation, Long> {

    boolean existsByBookIdAndUserId(Long bookId, Long userId);

}
