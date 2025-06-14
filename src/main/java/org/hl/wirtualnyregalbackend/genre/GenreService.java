package org.hl.wirtualnyregalbackend.genre;

import org.hl.wirtualnyregalbackend.common.model.PageResponseDto;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public PageResponseDto<GenreResponseDto> findGenres(Pageable pageable) {
        Locale locale = LocaleContextHolder.getLocale();
        Page<Genre> page = genreRepository.findAll(pageable);
        Page<GenreResponseDto> response = page.map((genre) -> GenreMapper.toGenreResponseDto(genre, locale));
        return new PageResponseDto<>(response, "genres");
    }

}
