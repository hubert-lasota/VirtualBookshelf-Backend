package org.hl.wirtualnyregalbackend.genre.dao;

import org.hl.wirtualnyregalbackend.genre.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreRepository {

    Set<Genre> findByIds(List<Long> ids);

}
