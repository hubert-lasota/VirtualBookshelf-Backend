package org.hl.wirtualnyregalbackend.infrastructure.book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.hl.wirtualnyregalbackend.application.book.Book;
import org.hl.wirtualnyregalbackend.application.book.exception.BookNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class JpaBookRepository implements BookRepository {

    private final String QUERY_FETCH_ALL_ASSOCIATIONS = """
        select b 
        from Book b 
        left join fetch b.authors
        left join fetch b.genres
        left join fetch b.tags
     """;

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
        String finalQuery = QUERY_FETCH_ALL_ASSOCIATIONS + " where b.id = :id";
        try {
            return entityManager.createQuery(finalQuery, Book.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            throw new BookNotFoundException("Book with id %s not found.".formatted(id));
        }
    }

    @Override
    public Optional<Book> findOptionalWithAllAssociationsById(Long id) {
        try {
            Book book = findWithAllAssociationsById(id);
            return Optional.of(book);
        } catch (BookNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> findOptionalWithAllAssociationsByExternalApiId(String externalApiId) {
        String finalQuery = QUERY_FETCH_ALL_ASSOCIATIONS + " where b.externalApiId = :externalApiId";
        try {
            Book book = entityManager.createQuery(finalQuery, Book.class)
                    .setParameter("externalApiId", externalApiId).getSingleResult();
            return Optional.of(book);
        } catch (NoResultException e) {
            return Optional.empty();
        }
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

    Optional<Book> findByExternalApiId(String externalApiId);

    @Query("select b.books from Bookshelf b where b.id = :bookshelfId")
    List<Book> findByBookshelfId(Long bookshelfId);

}
