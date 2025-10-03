package org.hl.wirtualnyregalbackend.common.translation;

import java.util.Collection;
import java.util.Locale;
import java.util.NoSuchElementException;

public class TranslationUtils {

    private TranslationUtils() {
    }

    public static String getTranslatedName(Collection<? extends TranslatedNamedEntity> entities,
                                           Locale locale) {
        return entities
            .stream()
            .filter((name) -> name.getTranslatedName().getLanguage().getLanguage().equals(locale.getLanguage()))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Could not find translated name for locale: %s and entities(type=%s): %s"
                .formatted(locale, entities.getClass().getSimpleName(), entities)))
            .getTranslatedName()
            .getName();
    }
}
