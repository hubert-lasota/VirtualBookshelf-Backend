package org.hl.wirtualnyregalbackend.book.dao;

import org.hl.wirtualnyregalbackend.book.model.entity.BookEdition;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
class JpaBookEditionRepository implements BookEditionRepository {

    private final SpringJpaBookEditionRepository bookEditionRepository;

    public JpaBookEditionRepository(SpringJpaBookEditionRepository bookEditionRepository) {
        this.bookEditionRepository = bookEditionRepository;
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
    public BookEdition findById(Long id) throws EntityNotFoundException {
        return bookEditionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Not found book edition with id %d".formatted(id)));
    }
}

@Repository
interface SpringJpaBookEditionRepository extends JpaRepository<BookEdition, Long>, JpaSpecificationExecutor<BookEdition> {

}