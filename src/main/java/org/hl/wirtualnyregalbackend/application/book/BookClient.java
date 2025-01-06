package org.hl.wirtualnyregalbackend.application.book;

import org.hl.wirtualnyregalbackend.application.book.exception.BookNotFoundException;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookClient {


    Page<BookResponse> searchBooks(String query, Pageable pageable);

    BookResponse findBookByIsbn(String isbn) throws BookNotFoundException;

    BookResponse findBookById(String id) throws BookNotFoundException;
}
