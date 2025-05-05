package org.hl.wirtualnyregalbackend.genre.dao;

import org.hl.wirtualnyregalbackend.genre.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
class JpaGenreRepository implements GenreRepository {

    private final SpringJpaGenreRepository bookGenreRepository;

    public JpaGenreRepository(SpringJpaGenreRepository bookGenreRepository) {
        this.bookGenreRepository = bookGenreRepository;
    }


    @Override
    public Set<Genre> findByIds(List<Long> ids) {
        return bookGenreRepository.findByIds(ids);
    }
}

@Repository
interface SpringJpaGenreRepository extends JpaRepository<Genre, Long> {

    @Query("select g from Genre g where g.id in (:ids)")
    Set<Genre> findByIds(List<Long> ids);

}