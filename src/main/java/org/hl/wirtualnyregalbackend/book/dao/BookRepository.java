package org.hl.wirtualnyregalbackend.book.dao;

import org.hl.wirtualnyregalbackend.book.exception.BookNotFoundException;
import org.hl.wirtualnyregalbackend.book.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book save(Book book);

    Book findById(Long id) throws BookNotFoundException;

    Optional<Book> findOptionalById(Long id);

    Optional<Book> findOptionalByExternalApiId(String externalApiId);

    Book findByExternalApiId(String externalApiId) throws BookNotFoundException;

    Book findWithAllAssociationsById(Long id) throws BookNotFoundException;

    Optional<Book> findOptionalWithAllAssociationsById(Long id);

    Optional<Book> findOptionalWithAllAssociationsByExternalApiId(String externalApiId);

    List<Book> findByBookshelfId(Long bookshelfId);

    boolean existsById(Long id);

}
