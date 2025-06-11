package org.hl.wirtualnyregalbackend.book_review;

import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface BookReviewRepository extends JpaRepository<BookReview, Long> {

    @Query("select count(b) > 0 from BookReview b where b.id = :bookRatingId and b.user.id = :userId")
    boolean isAuthor(Long bookRatingId, Long userId);

    @Query("select avg(b.rating) from BookReview b where b.book.id = :bookId")
    Float findRatingAverage(Long bookId);

    Page<BookReview> findByBookId(Long bookId, Pageable pageable);

}
