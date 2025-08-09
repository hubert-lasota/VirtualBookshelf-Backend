package org.hl.wirtualnyregalbackend.book_format;

import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface BookFormatRepository extends JpaRepository<BookFormat, Long>, JpaSpecificationExecutor<BookFormat> {

}
