package org.hl.wirtualnyregalbackend.book.dao;

import org.hl.wirtualnyregalbackend.book.exception.BookRatingNotFoundException;
import org.hl.wirtualnyregalbackend.book.model.BookRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
class JpaBookRatingRepository implements BookRatingRepository {

    private final SpringJpaBookRatingRepository bookRatingRepository;

    JpaBookRatingRepository(SpringJpaBookRatingRepository bookRatingRepository) {
        this.bookRatingRepository = bookRatingRepository;
    }

    @Override
    public BookRating save(BookRating bookRating) {
        return bookRatingRepository.save(bookRating);
    }

    @Override
    public BookRating findById(Long id) {
        return bookRatingRepository.findById(id)
                .orElseThrow(() -> new BookRatingNotFoundException("Book Rating with id: %d not found".formatted(id)));
    }

    @Override
    public void deleteById(Long id) {
        bookRatingRepository.deleteById(id);
    }

    @Override
    public boolean isBookRatingAuthor(Long bookRatingId, Long userId) {
        return bookRatingRepository.isBookRatingAuthor(bookRatingId, userId);
    }

    @Override
    public Float getBookRatingAverage(Long bookId) {
        return bookRatingRepository.getBookRatingAverage(bookId);
    }

    @Override
    public Page<BookRating> findRatingPageByBookId(Long bookId, Pageable pageable) {
        return bookRatingRepository.findRatingPageByBookId(bookId, pageable);
    }

    @Override
    public boolean existsById(Long id) {
        return bookRatingRepository.existsById(id);
    }

}

@Repository
interface SpringJpaBookRatingRepository extends JpaRepository<BookRating, Long> {

    @Query("select count(br) > 0 from BookRating br where br.id = :bookRatingId and br.user.id = :userId")
    boolean isBookRatingAuthor(Long bookRatingId, Long userId);


    @Query("select avg(br.rating) from BookRating br where br.book.id = :bookId")
    Float getBookRatingAverage(Long bookId);

    @Query("select br from BookRating br where br.book.id = :bookId")
    Page<BookRating> findRatingPageByBookId(Long bookId, Pageable pageable);

}