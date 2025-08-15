package org.hl.wirtualnyregalbackend.reading_book;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ReadingBookRepository extends JpaRepository<ReadingBook, Long>, JpaSpecificationExecutor<ReadingBook> {

    @Query("select count(b) from ReadingBook b where b.bookshelf.id = :bookshelfId")
    Long countBooks(Long bookshelfId);

    @Query("select rb from ReadingBook rb join rb.bookshelf b where rb.book.id = :bookId and b.user.id = :userId")
    Optional<ReadingBook> findByBookIdAndUserId(Long bookId, Long userId);
}
