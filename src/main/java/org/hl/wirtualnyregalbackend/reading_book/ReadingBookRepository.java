package org.hl.wirtualnyregalbackend.reading_book;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ReadingBookRepository extends JpaRepository<ReadingBook, Long>, JpaSpecificationExecutor<ReadingBook> {

    @Query("select rb from ReadingBook rb join rb.bookshelf b where rb.book.id = :bookId and b.user.id = :userId")
    Optional<ReadingBook> findByBookIdAndUserId(Long bookId, Long userId);

    @Query("select count(rb) > 0 from ReadingBook rb where rb.id = :readingBookId and rb.bookshelf.user.id = :userId")
    boolean isAuthor(Long readingBookId, Long userId);

    @Query("""
            select count(distinct g.id)
            from Genre g
            where g.id in :genreIds
            and not exists (
                select 1
                from ReadingBook rb
                join rb.book.genres gg
                where gg.id = g.id
                  and rb.bookshelf.user.id = :userId
                  and rb.status = :status
            )
        """)
    int countGenresByUserAndReadingStatus(List<Long> genreIds, Long userId, ReadingStatus status);

    @Query("""
            select count(distinct a.id)
            from Author a
            where a.id in :authorIds
              and not exists (
                  select 1
                  from ReadingBook rb
                  join rb.book.authors aa
                  where aa.id = a.id
                    and rb.bookshelf.user.id = :userId
                    and rb.status = :status
              )
        """)
    int countAuthorsByUserAndReadingStatus(List<Long> authorIds, Long userId, ReadingStatus status);

}
