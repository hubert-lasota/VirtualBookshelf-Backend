package org.hl.wirtualnyregalbackend.reading_book;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ReadingBookRepository extends JpaRepository<ReadingBook, Long>, JpaSpecificationExecutor<ReadingBook> {

    @Query("select rb from ReadingBook rb join rb.bookshelf b where rb.book.id = :bookId and b.user.id = :userId")
    Optional<ReadingBook> findByBookIdAndUserId(Long bookId, Long userId);

    @Query("select count(rb) > 0 from ReadingBook rb where rb.id = :readingBookId and rb.bookshelf.user.id = :userId")
    boolean isAuthor(Long readingBookId, Long userId);

}
