package org.hl.wirtualnyregalbackend.genre;

import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.genre.dto.GenreListResponse;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.genre.exception.GenreNotFoundException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Set<Genre> findGenresByIds(List<Long> ids) {
        return genreRepository.findByIds(ids);
    }

    public GenreListResponse findGenres(Boolean availableInBookshelf, User user) {
        Locale locale = LocaleContextHolder.getLocale();
        Specification<Genre> spec = Specification.where(null);
        if (availableInBookshelf != null) {
            spec = spec.and(GenreSpecification.byAvailableInBookshelf(availableInBookshelf, user));
        }
        List<GenreResponse> genres = genreRepository
            .findAll(spec)
            .stream()
            .map((genre) -> GenreMapper.toGenreResponse(genre, locale))
            .toList();
        return new GenreListResponse(genres);
    }

    public Genre findGenreById(Long genreId) throws GenreNotFoundException {
        Optional<Genre> genreOpt = genreId == null
            ? Optional.empty()
            : genreRepository.findById(genreId);
        return genreOpt.orElseThrow(() -> {
            log.warn("Genre not found with ID: {}", genreId);
            return new GenreNotFoundException(genreId);
        });
    }

}
