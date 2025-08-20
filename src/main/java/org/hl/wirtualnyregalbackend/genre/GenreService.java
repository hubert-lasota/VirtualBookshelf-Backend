package org.hl.wirtualnyregalbackend.genre;

import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.genre.dto.GenrePageResponse;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
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

    public GenrePageResponse findGenres(Pageable pageable) {
        Locale locale = LocaleContextHolder.getLocale();
        Page<GenreResponse> page = genreRepository
            .findAll(pageable)
            .map((genre) -> GenreMapper.toGenreResponse(genre, locale));
        return GenrePageResponse.from(page);
    }

    public Genre findGenreById(Long genreId) throws EntityNotFoundException {
        Optional<Genre> genreOpt = genreId == null
            ? Optional.empty()
            : genreRepository.findById(genreId);
        return genreOpt.orElseThrow(() -> new EntityNotFoundException("Genre with id = '%d' not found.".formatted(genreId)));
    }

}
