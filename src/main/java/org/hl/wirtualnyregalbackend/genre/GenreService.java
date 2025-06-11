package org.hl.wirtualnyregalbackend.genre;

import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
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

    public List<GenreResponseDto> findGenres() {
        Locale locale = LocaleContextHolder.getLocale();
        return genreRepository.findAll()
            .stream()
            .map((genre) -> GenreMapper.toGenreResponseDto(genre, locale))
            .toList();
    }

}
