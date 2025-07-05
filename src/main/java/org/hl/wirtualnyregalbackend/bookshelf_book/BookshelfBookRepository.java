package org.hl.wirtualnyregalbackend.bookshelf_book;

import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BookshelfBookRepository extends JpaRepository<BookshelfBook, Long> {

    @Query("select count(b) from BookshelfBook b where b.bookshelf.id = :bookshelfId")
    Long countBooks(Long bookshelfId);

    @Query("select b from BookshelfBook b where b.bookshelf.user.id = :userId")
    List<BookshelfBook> findBookshelfBooksByUserId(Long userId);

}
