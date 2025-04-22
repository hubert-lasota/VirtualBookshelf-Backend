package org.hl.wirtualnyregalbackend.genre.dao;

import org.hl.wirtualnyregalbackend.genre.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
class JpaGenreRepository implements GenreRepository {

    private final SpringJpaGenreRepository bookGenreRepository;

    public JpaGenreRepository(SpringJpaGenreRepository bookGenreRepository) {
        this.bookGenreRepository = bookGenreRepository;
    }

    @Override
    public List<Genre> saveAll(List<Genre> genres) {
        return bookGenreRepository.saveAll(genres);
    }

    @Override
    public List<Genre> findByNamesIgnoreCase(Collection<String> names) {
        return bookGenreRepository.findByNamesIgnoreCase(names);
    }


}

@Repository
interface SpringJpaGenreRepository extends JpaRepository<Genre, Long> {

    @Query("select g from Genre g join g.names name where name in (:names)")
    List<Genre> findByNamesIgnoreCase(Collection<String> names);

}