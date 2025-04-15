package org.hl.wirtualnyregalbackend.genre.dao;

import org.hl.wirtualnyregalbackend.genre.model.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreRepository {

    List<Genre> saveAll(List<Genre> genres);

    List<Genre> findByNamesIgnoreCase(Collection<String> names);

}
