package org.hl.wirtualnyregalbackend.book.dao;

import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
class JpaBookRepository implements BookRepository {

    private final SpringJpaBookRepository bookRepository;

    public JpaBookRepository(SpringJpaBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book findById(Long id) throws EntityNotFoundException {
        return bookRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found.".formatted(id)));
    }

    @Override
    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

}

@Repository
interface SpringJpaBookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}

