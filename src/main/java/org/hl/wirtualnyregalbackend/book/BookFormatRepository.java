package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.book.model.entity.BookFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookFormatRepository extends JpaRepository<BookFormat, Long> {

}
