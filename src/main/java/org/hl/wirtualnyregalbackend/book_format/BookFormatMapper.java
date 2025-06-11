package org.hl.wirtualnyregalbackend.book_format;

import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.hl.wirtualnyregalbackend.common.translation.TranslationUtils;

import java.util.Locale;

public class BookFormatMapper {

    private BookFormatMapper() {
    }

    public static BookFormatDto toBookFormatDto(BookFormat bookFormat, Locale locale) {
        String localizedName = TranslationUtils.getTranslatedName(bookFormat.getTranslations(), locale);
        return new BookFormatDto(bookFormat.getId(), localizedName);
    }

}
