package org.hl.wirtualnyregalbackend.genre;

import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.genre.dto.GenrePageResponseDto;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
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

    public GenrePageResponseDto findGenres(Pageable pageable) {
        Locale locale = LocaleContextHolder.getLocale();
        Page<GenreResponseDto> page = genreRepository
            .findAll(pageable)
            .map((genre) -> GenreMapper.toGenreResponseDto(genre, locale));
        return GenrePageResponseDto.from(page);
    }

    public Genre findGenreById(Long genreId) throws EntityNotFoundException {
        Optional<Genre> genreOpt = genreId == null
            ? Optional.empty()
            : genreRepository.findById(genreId);
        return genreOpt.orElseThrow(() -> new EntityNotFoundException("Genre with id = '%d' not found.".formatted(genreId)));
    }

}
