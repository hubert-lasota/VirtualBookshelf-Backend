package org.hl.wirtualnyregalbackend.author_review;

import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;
import org.hl.wirtualnyregalbackend.common.review.ReviewStatistics;
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

    @Query("""
        select new org.hl.wirtualnyregalbackend.common.review.ReviewStatistics(
            a.author.id,
            avg(a.rating),
            count(a)
        )
        from AuthorReview a
        where a.author.id in :authorId
        group by a.author.id
        """)
    Optional<ReviewStatistics> getReviewStatsByAuthorId(Long authorId);

    Page<AuthorReview> findByAuthorId(Long authorId, Pageable pageable);

}