package org.hl.wirtualnyregalbackend.review.book_review.dao;

import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.review.book_review.model.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
class JpaBookReviewRepository implements BookReviewRepository {

    private final SpringJpaBookReviewRepository bookReviewRepository;

    JpaBookReviewRepository(SpringJpaBookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    @Override
    public BookReview save(BookReview bookReview) {
        return bookReviewRepository.save(bookReview);
    }

    @Override
    public BookReview findById(Long id) {
        return bookReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book Rating with id: %d not found".formatted(id)));
    }

    @Override
    public void deleteById(Long id) {
        bookReviewRepository.deleteById(id);
    }

    @Override
    public boolean isAuthor(Long bookRatingId, Long userId) {
        return bookReviewRepository.isAuthor(bookRatingId, userId);
    }

    @Override
    public Float getRatingAverage(Long bookId) {
        return bookReviewRepository.getBookRatingAverage(bookId);
    }

    @Override
    public Page<BookReview> findPageByBookId(Long bookId, Pageable pageable) {
        return bookReviewRepository.findRatingPageByBookId(bookId, pageable);
    }

    @Override
    public boolean existsById(Long id) {
        return bookReviewRepository.existsById(id);
    }

}

@Repository
interface SpringJpaBookReviewRepository extends JpaRepository<BookReview, Long> {

    @Query("select count(br) > 0 from BookReview br where br.id = :bookRatingId and br.user.id = :userId")
    boolean isAuthor(Long bookRatingId, Long userId);


    @Query("select avg(br.rating) from BookReview br where br.book.id = :bookId")
    Float getBookRatingAverage(Long bookId);

    @Query("select br from BookReview br where br.book.id = :bookId")
    Page<BookReview> findRatingPageByBookId(Long bookId, Pageable pageable);

}