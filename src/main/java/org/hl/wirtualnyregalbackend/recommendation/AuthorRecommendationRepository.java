package org.hl.wirtualnyregalbackend.recommendation;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.recommendation.entity.AuthorRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface AuthorRecommendationRepository extends JpaRepository<AuthorRecommendation, Long> {

    Optional<AuthorRecommendation> findByAuthorAndUser(Author author, User user);

}
