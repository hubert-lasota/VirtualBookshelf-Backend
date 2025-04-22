package org.hl.wirtualnyregalbackend.book.dao;

import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.book.model.entity.BookEdition;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class JpaBookRepository implements BookRepository {

    private final SpringJpaBookRepository bookRepository;
    private final SpringJpaBookEditionRepository bookEditionRepository;

    public JpaBookRepository(SpringJpaBookRepository bookRepository, SpringJpaBookEditionRepository bookEditionRepository) {
        this.bookRepository = bookRepository;
        this.bookEditionRepository = bookEditionRepository;
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
    public Page<BookEdition> findByQuery(String query, Pageable pageable) {
        Specification<BookEdition> spec = Specification
            .where(BookEditionSpecification.titleIgnoreCaseLike(query))
            .or(BookEditionSpecification.authorFullNameIgnoreCaseLike(query))
            .or(BookEditionSpecification.isbnEqual(query));

        return bookEditionRepository.findAll(spec, pageable);
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

    @Query("select b.books from Bookshelf b where b.id = :bookshelfId")
    List<Book> findByBookshelfId(Long bookshelfId);

}

@Repository
interface SpringJpaBookEditionRepository extends JpaRepository<BookEdition, Long>, JpaSpecificationExecutor<BookEdition> {

}