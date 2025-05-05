package org.hl.wirtualnyregalbackend.genre;

import org.hl.wirtualnyregalbackend.genre.dao.GenreRepository;
import org.hl.wirtualnyregalbackend.genre.model.Genre;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Set<Genre> findGenresByIds(List<Long> ids) {
        return genreRepository.findByIds(ids);
    }

}
