package org.hl.wirtualnyregalbackend.infrastructure.book;

import org.hl.wirtualnyregalbackend.application.book.BookRating;
import org.hl.wirtualnyregalbackend.application.book.exception.BookRatingNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
class JpaBookRatingRepository implements BookRatingRepository {

    private final SpringJpaBookRatingRepository bookRatingRepository;

    public JpaBookRatingRepository(SpringJpaBookRatingRepository bookRatingRepository) {
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
}

@Repository
interface SpringJpaBookRatingRepository extends JpaRepository<BookRating, Long> {

    @Query("select count(br) > 0 from BookRating br where br.id = :bookRatingId and br.user.id = :userId")
    boolean isBookRatingAuthor(Long bookRatingId, Long userId);
}