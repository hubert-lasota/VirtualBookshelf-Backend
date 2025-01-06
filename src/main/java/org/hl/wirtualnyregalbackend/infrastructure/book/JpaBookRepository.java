package org.hl.wirtualnyregalbackend.infrastructure.book;

import org.hl.wirtualnyregalbackend.application.book.Book;
import org.hl.wirtualnyregalbackend.application.book.exception.BookNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class JpaBookRepository implements BookRepository {

    private final SpringJpaBookRepository bookRepository;

    public JpaBookRepository(SpringJpaBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book findById(Long id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id %d not found.".formatted(id)));
    }

    @Override
    public Optional<Book> findOptionalById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book findByExternalApiId(String externalApiId) throws BookNotFoundException {
        return bookRepository.findByExternalApiId(externalApiId)
                .orElseThrow(() -> new BookNotFoundException("Book with id %s not found.".formatted(externalApiId)));
    }

    @Override
    public Optional<Book> findOptionalByExternalApiId(String externalApiId) {
        return bookRepository.findByExternalApiId(externalApiId);
    }

    @Override
    public Book findWithAllAssociationsById(Long id) throws BookNotFoundException {
        return bookRepository.findWithAllAssociationsById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id %d not found.".formatted(id)));
    }

    @Override
    public Optional<Book> findOptionalWithAllAssociationsById(Long id) throws BookNotFoundException {
        return bookRepository.findWithAllAssociationsById(id);
    }

    @Override
    public Optional<Book> findOptionalWithAllAssociationsByExternalApiId(String externalApiId) {
        return bookRepository.findWithAllAssociationsByExternalApiId(externalApiId);
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
interface SpringJpaBookRepository extends JpaRepository<Book, Long> {

    @Query("""
        select b 
        from Book b 
        join fetch b.authors
        left join fetch b.bookRatings 
        left join fetch b.bookGenres
        where b.id = :id  
    """)
    Optional<Book> findWithAllAssociationsById(Long id);

    @Query("""
        select b 
        from Book b 
        join fetch b.authors
        left join fetch b.bookRatings 
        left join fetch b.bookGenres
        where b.externalApiId = :externalApiId  
    """)
    Optional<Book> findWithAllAssociationsByExternalApiId(String externalApiId);

    Optional<Book> findByExternalApiId(String externalApiId);

    Optional<Book> findByIsbn(String isbn);

    @Query("select b.books from Bookshelf b where b.id = :bookshelfId")
    List<Book> findByBookshelfId(Long bookshelfId);

}
