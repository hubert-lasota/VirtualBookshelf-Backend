package org.hl.wirtualnyregalbackend.book.dao;

import org.hl.wirtualnyregalbackend.book.model.dto.response.BookSearchResponseDto;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRepository {

    Book save(Book book);

    Book findById(Long id) throws EntityNotFoundException;

    Page<BookSearchResponseDto> findByQuery(String query, Pageable pageable);

    List<Book> findByBookshelfId(Long bookshelfId);

    boolean existsById(Long id);

}
