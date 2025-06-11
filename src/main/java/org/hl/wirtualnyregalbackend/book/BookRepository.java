package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

}
