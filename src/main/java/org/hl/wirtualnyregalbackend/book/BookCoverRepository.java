package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.book.model.entity.BookCover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookCoverRepository extends JpaRepository<BookCover, Long> {
}
