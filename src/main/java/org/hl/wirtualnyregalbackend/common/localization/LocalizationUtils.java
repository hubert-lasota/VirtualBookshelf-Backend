package org.hl.wirtualnyregalbackend.common.localization;

import java.util.Collection;
import java.util.Locale;

public class LocalizationUtils {

    private LocalizationUtils() { }

    public static String getLocalizedName(Collection<? extends  LocalizedNamedEntity> localizedNamedEntities,
                                          Locale locale) {
        return localizedNamedEntities.stream()
            .filter((name) -> name.getLocalizedName().getLanguage().equals(locale))
            .findFirst()
            .orElseThrow()
            .getLocalizedName()
            .getName();
    }
}
