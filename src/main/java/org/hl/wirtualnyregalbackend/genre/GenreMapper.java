package org.hl.wirtualnyregalbackend.genre;

import org.hl.wirtualnyregalbackend.common.localization.LocalizationUtils;
import org.hl.wirtualnyregalbackend.genre.model.Genre;
import org.hl.wirtualnyregalbackend.genre.model.dto.GenreDto;

import java.util.Locale;

public class GenreMapper {

    private GenreMapper() { }

    public static GenreDto toGenreDto(Genre genre, Locale locale) {
        String localizedName = LocalizationUtils.getLocalizedName(genre.getNames(), locale);
        return new GenreDto(genre.getId(), localizedName);
    }

}
