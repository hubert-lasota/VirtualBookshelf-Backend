package org.hl.wirtualnyregalbackend.genre;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.genre.dto.GenreListResponse;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.genre.exception.GenreNotFoundException;
import org.hl.wirtualnyregalbackend.genre.model.GenreFilter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class GenreQueryService {

    private final GenreRepository repository;


    public Set<Genre> findGenresByIds(List<Long> ids) {
        return repository.findByIds(ids);
    }

    public GenreListResponse findRecommendedGenres(GenreFilter filter, User recommendedFor) {
        return findGenres(true, filter, recommendedFor);
    }

    GenreListResponse findGenres(GenreFilter filter, User user) {
        return findGenres(false, filter, user);
    }

    private GenreListResponse findGenres(boolean sortByRecommendation, GenreFilter filter, User user) {
        Locale locale = LocaleContextHolder.getLocale();
        var spec = GenreSpecification.byFilterAndUser(filter, user);
        if (sortByRecommendation) {
            spec = spec.and(GenreSpecification.sortByRecommendation(user));
        }
        List<GenreResponse> genres = repository
            .findAll(spec)
            .stream()
            .map((genre) -> GenreMapper.toGenreResponse(genre, locale))
            .toList();
        return new GenreListResponse(genres);
    }

    public Genre findGenreById(Long genreId) throws GenreNotFoundException {
        Optional<Genre> genreOpt = genreId == null
            ? Optional.empty()
            : repository.findById(genreId);
        return genreOpt.orElseThrow(() -> {
            log.warn("Genre not found with ID: {}", genreId);
            return new GenreNotFoundException(genreId);
        });
    }

}
