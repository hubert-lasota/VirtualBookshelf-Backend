package org.hl.wirtualnyregalbackend.book.dao;

import org.hl.wirtualnyregalbackend.book.model.entity.BookFormat;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
class JpaBookFormatRepository implements BookFormatRepository {

    private final SpringJpaBookFormatRepository bookFormatRepository;

    public JpaBookFormatRepository(SpringJpaBookFormatRepository bookFormatRepository) {
        this.bookFormatRepository = bookFormatRepository;
    }

    @Override
    @Transactional
    public BookFormat findById(Long id) {
        return bookFormatRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Not found BookFormat with id = '%d'".formatted(id)));
    }
}

@Repository
interface SpringJpaBookFormatRepository extends JpaRepository<BookFormat, Long> {
}