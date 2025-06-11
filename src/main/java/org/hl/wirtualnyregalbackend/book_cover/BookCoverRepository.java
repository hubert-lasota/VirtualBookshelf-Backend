package org.hl.wirtualnyregalbackend.book_cover;

import org.hl.wirtualnyregalbackend.book_cover.entity.BookCover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookCoverRepository extends JpaRepository<BookCover, Long> {
}
