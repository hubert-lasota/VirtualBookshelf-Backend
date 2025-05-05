package org.hl.wirtualnyregalbackend.book.dao;

import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;

public interface BookRepository {

    Book save(Book book);

    Book findById(Long id) throws EntityNotFoundException;

    boolean existsById(Long id);

}
