package org.hl.wirtualnyregalbackend.book.dao;

import org.hl.wirtualnyregalbackend.book.model.entity.BookEdition;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookEditionRepository {

    Page<BookEdition> findByQuery(String query, Pageable pageable);

    BookEdition findById(Long id) throws EntityNotFoundException;

}
