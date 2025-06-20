package org.hl.wirtualnyregalbackend.genre;

import org.hl.wirtualnyregalbackend.common.translation.TranslationUtils;
import org.hl.wirtualnyregalbackend.genre.dto.GenreMutationDto;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;

import java.util.Locale;

public class GenreMapper {

    private GenreMapper() {
    }

    public static GenreResponseDto toGenreResponseDto(Genre genre, Locale locale) {
        String localizedName = TranslationUtils.getTranslatedName(genre.getTranslations(), locale);
        GenreMutationDto dto = new GenreMutationDto(localizedName);
        return new GenreResponseDto(genre.getId(), genre.getCreatedAt(), genre.getUpdatedAt(), dto);
    }

}
