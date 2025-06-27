package org.hl.wirtualnyregalbackend.book_review;

import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;
import org.hl.wirtualnyregalbackend.common.review.ReviewStats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BookReviewRepository extends JpaRepository<BookReview, Long> {

    @Query("select count(b) > 0 from BookReview b where b.id = :bookRatingId and b.user.id = :userId")
    boolean isAuthor(Long bookRatingId, Long userId);

    Page<BookReview> findByBookId(Long bookId, Pageable pageable);

    @Query("""
        select new org.hl.wirtualnyregalbackend.common.review.ReviewStats(
            b.book.id,
            avg(b.rating),
            count(b)
        )
        from BookReview b
        where b.book.id in :bookIds
        group by b.book.id
        """)
    List<ReviewStats> getReviewStatsByBookIds(List<Long> bookIds);

}
