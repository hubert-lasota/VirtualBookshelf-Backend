package org.hl.wirtualnyregalbackend.bookshelf_book;

import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BookshelfBookRepository extends JpaRepository<BookshelfBook, Long>, JpaSpecificationExecutor<BookshelfBook> {

    @Query("select count(b) from BookshelfBook b where b.bookshelf.id = :bookshelfId")
    Long countBooks(Long bookshelfId);

}
