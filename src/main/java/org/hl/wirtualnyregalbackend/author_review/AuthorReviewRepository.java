package org.hl.wirtualnyregalbackend.author_review;

import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface AuthorReviewRepository extends JpaRepository<AuthorReview, Long> {

    @Query("select count(a) > 0 from AuthorReview a where a.author.id = :authorId and a.user.id = :userId")
    boolean existsByAuthorIdAndUserId(Long authorId, Long userId);

    @Query("select count(a) > 0 from AuthorReview a where a.id = :authorReviewId and a.user.id = :userId")
    boolean isAuthor(Long authorReviewId, Long userId);

    Page<AuthorReview> findByAuthorId(Long authorId, Pageable pageable);

    Optional<AuthorReview> findByAuthorIdAndUserId(Long authorId, Long userId);

    @Query("select avg(ar.rating) from AuthorReview ar where ar.author = :author")
    Double calculateAverageRatingByAuthor(Author author);

}