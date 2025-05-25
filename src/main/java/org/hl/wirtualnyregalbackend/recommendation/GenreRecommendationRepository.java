package org.hl.wirtualnyregalbackend.recommendation;

import org.hl.wirtualnyregalbackend.recommendation.model.GenreRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface GenreRecommendationRepository extends JpaRepository<GenreRecommendation, Long> {

    @Query("""
            select g.id 
            from GenreRecommendation gr 
            join Genre g 
            where gr.user.id=:userId and g.id not in :genreIds
        """)
    List<Long> findNonExistingGenreIds(List<Long> genreIds, Long userId);

}
