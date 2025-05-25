package org.hl.wirtualnyregalbackend.common.translation;

import java.util.Collection;
import java.util.Locale;

public class TranslationUtils {

    private TranslationUtils() {
    }

    public static String getTranslatedName(Collection<? extends TranslatedNamedEntity> entities,
                                           Locale locale) {
        return entities.stream()
            .filter((name) -> name.getTranslatedName().getLanguage().equals(locale))
            .findFirst()
            .orElseThrow()
            .getTranslatedName()
            .getName();
    }
}
