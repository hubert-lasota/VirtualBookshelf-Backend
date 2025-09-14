package org.hl.wirtualnyregalbackend.recommendation;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.recommendation.entity.GenreRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface GenreRecommendationRepository extends JpaRepository<GenreRecommendation, Long> {

    Optional<GenreRecommendation> findByGenreAndUser(Genre genre, User user);

}
