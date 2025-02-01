package org.hl.wirtualnyregalbackend.infrastructure.book;

import org.hl.wirtualnyregalbackend.application.book.BookReadingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
class JpaBookReadingDetailsRepository implements BookReadingDetailsRepository {

    private final SpringJpaBookReadingDetailsRepository bookReadingDetailsRepository;

    JpaBookReadingDetailsRepository(SpringJpaBookReadingDetailsRepository bookReadingDetailsRepository) {
        this.bookReadingDetailsRepository = bookReadingDetailsRepository;
    }


    @Override
    public BookReadingDetails findByBookIdAndUserId(Long bookId, Long userId) {
        return null;
    }
}

@Repository
interface SpringJpaBookReadingDetailsRepository extends JpaRepository<BookReadingDetails, Long> {

    @Query("select b from BookReadingDetails b where b.book.id = :bookId and b.user.id =:userId")
    BookReadingDetails findByBookIdAndUserId(Long bookId, Long userId);

}