package org.hl.wirtualnyregalbackend.genre;

import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("select g from Genre g where g.id in (:ids)")
    Set<Genre> findByIds(List<Long> ids);

}
