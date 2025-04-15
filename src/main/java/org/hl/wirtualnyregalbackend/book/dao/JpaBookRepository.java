package org.hl.wirtualnyregalbackend.book.dao;

import jakarta.persistence.EntityManager;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookSearchResponseDto;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class JpaBookRepository implements BookRepository {

    private final SpringJpaBookRepository bookRepository;
    private final EntityManager entityManager;

    public JpaBookRepository(SpringJpaBookRepository bookRepository, EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.entityManager = entityManager;
    }


    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book findById(Long id) throws EntityNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found.".formatted(id)));
    }

    @Override
    public Page<BookSearchResponseDto> findByQuery(String query, Pageable pageable) {
        return null;
    }

    @Override
    public List<Book> findByBookshelfId(Long bookshelfId) {
        return bookRepository.findByBookshelfId(bookshelfId);
    }

    @Override
    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

}

@Repository
interface SpringJpaBookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {


    @Query("""
        SELECT new org.hl.wirtualnyregalbackend.book.model.dto.response.BookSearchResponseDto(
            b.id, (SELECT e2.title FROM b.editions LIMIT 1), 
            )
        FROM Book b
        JOIN b.editions e
        JOIN b.authors a
        LEFT JOIN b.cover
        WHERE LOWER(e.title) LIKE %:query%
            OR LOWER(e.isbn) LIKE %:query%
            OR LOWER(a.fullName) LIKE %:query%
    """)
    Page<BookSearchResponseDto> findByQuery(String query, Pageable pageable);

    @Query("select b.books from Bookshelf b where b.id = :bookshelfId")
    List<Book> findByBookshelfId(Long bookshelfId);

}
