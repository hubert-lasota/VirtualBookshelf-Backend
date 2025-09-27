package org.hl.wirtualnyregalbackend.book_review;

import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface BookReviewRepository extends JpaRepository<BookReview, Long> {

    @Query("select count(b) > 0 from BookReview b where b.id = :bookReviewId and b.user.id = :userId")
    boolean isAuthor(Long bookReviewId, Long userId);

    Page<BookReview> findByBookId(Long bookId, Pageable pageable);

    @Query("select count(b) > 0 from BookReview b where b.book.id = :bookId and b.user.id = :userId")
    boolean existsByBookIdAndUserId(Long bookId, Long userId);

    @Query("select avg(br.rating) from BookReview br where br.book = :book")
    Double calculateAverageRatingByBook(Book book);

    Optional<BookReview> findByBookIdAndUserId(Long bookId, Long userId);

}
