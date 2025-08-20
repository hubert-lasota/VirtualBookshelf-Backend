package org.hl.wirtualnyregalbackend.genre;

import org.hl.wirtualnyregalbackend.common.translation.TranslationUtils;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;

import java.util.Locale;

public class GenreMapper {

    private GenreMapper() {
    }

    public static GenreResponse toGenreResponse(Genre genre, Locale locale) {
        String localizedName = TranslationUtils.getTranslatedName(genre.getTranslations(), locale);
        return new GenreResponse(genre.getId(), localizedName);
    }

}
