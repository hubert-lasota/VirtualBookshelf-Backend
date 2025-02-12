package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.book.exception.BookNotFoundException;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookClient {


    Page<BookResponse> searchBooks(String query, Pageable pageable);

    BookResponse findBookByIsbn(String isbn) throws BookNotFoundException;

    BookResponse findBookById(String id) throws BookNotFoundException;
}
