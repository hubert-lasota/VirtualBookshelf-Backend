package org.hl.wirtualnyregalbackend.infrastructure.bookshelf;

import org.hl.wirtualnyregalbackend.application.bookshelf.Bookshelf;
import org.hl.wirtualnyregalbackend.application.bookshelf.exception.BookshelfNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class JpaBookshelfRepository implements BookshelfRepository {

    private final SpringJpaBookshelfRepository bookshelfRepository;

    public JpaBookshelfRepository(SpringJpaBookshelfRepository springJpaBookshelfRepository) {
        this.bookshelfRepository = springJpaBookshelfRepository;
    }

    @Override
    public Bookshelf save(Bookshelf entity) {
        return bookshelfRepository.save(entity);
    }

    @Override
    public void saveAll(List<Bookshelf> entities) {
        bookshelfRepository.saveAll(entities);
    }

    @Override
    public Bookshelf findWithBooksById(Long id) {
        return bookshelfRepository.findWithBooksById(id)
                .orElseThrow(() -> new BookshelfNotFoundException("Bookshelf with id %d not found.".formatted(id)));
    }

    @Override
    public List<Bookshelf> findByUserId(Long userId) {
        return bookshelfRepository.findByUserId(userId);
    }

    @Override
    public boolean isUserBookshelfAuthor(Long bookshelfId, Long userId) {
        return bookshelfRepository.isUserBookshelfAuthor(bookshelfId, userId);
    }
}

@Repository
interface SpringJpaBookshelfRepository extends JpaRepository<Bookshelf, Long> {

    @Query("select b from Bookshelf b left join fetch b.books where b.id = :id")
    Optional<Bookshelf> findWithBooksById(Long id);

    @Query("select count(b.id) > 0 from Bookshelf b where b.id = :bookshelfId and b.user.id = :userId")
    boolean isUserBookshelfAuthor(Long bookshelfId, Long userId);

    @Query("select b from Bookshelf b left join fetch b.books where b.user.id = :userId")
    List<Bookshelf> findByUserId(Long userId);
}

