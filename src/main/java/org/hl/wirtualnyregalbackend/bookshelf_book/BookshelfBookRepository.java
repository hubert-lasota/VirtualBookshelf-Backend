package org.hl.wirtualnyregalbackend.bookshelf_book;

import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookshelfBookRepository extends JpaRepository<BookshelfBook, Long> {

}
