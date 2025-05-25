package org.hl.wirtualnyregalbackend.book_reading;

import org.hl.wirtualnyregalbackend.book_reading.model.BookReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface BookReadingRepository extends JpaRepository<BookReading, Long> {

    @Query("select b from BookReading b where b.book.id = :bookId and b.user.id =:userId")
    BookReading findByBookIdAndUserId(Long bookId, Long userId);

}
