package org.hl.wirtualnyregalbackend.infrastructure.book;

import org.hl.wirtualnyregalbackend.application.book.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
class JpaBookGenreRepository implements BookGenreRepository {

    private final SpringJpaBookGenreRepository bookGenreRepository;

    public JpaBookGenreRepository(SpringJpaBookGenreRepository bookGenreRepository) {
        this.bookGenreRepository = bookGenreRepository;
    }

    @Override
    public List<BookGenre> saveAll(List<BookGenre> bookGenres) {
        return bookGenreRepository.saveAll(bookGenres);
    }

    @Override
    public List<BookGenre> findByNamesIgnoreCase(Collection<String> names) {
        return bookGenreRepository.findByNamesIgnoreCase(names);
    }


}

@Repository
interface SpringJpaBookGenreRepository extends JpaRepository<BookGenre, Long> {

    @Query("select bg from BookGenre bg where lower(bg.name) in :names")
    List<BookGenre> findByNamesIgnoreCase(Collection<String> names);

}