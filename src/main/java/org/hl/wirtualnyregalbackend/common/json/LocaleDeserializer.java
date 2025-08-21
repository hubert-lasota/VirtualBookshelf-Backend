package org.hl.wirtualnyregalbackend.common.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.hl.wirtualnyregalbackend.common.error.exception.InvalidLanguageCodeException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class LocaleDeserializer extends JsonDeserializer<Locale> {

    @Override
    public Locale deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String raw = p.getValueAsString();
        if (raw == null || raw.isBlank()) {
            throw new InvalidLanguageCodeException("Language code not provided");
        }

        String langCode = raw.trim().toLowerCase(Locale.ROOT);

        if (!langCode.matches("^[a-z]{2,3}$")) {
            throw new InvalidLanguageCodeException("Invalid language code: %s".formatted(langCode));
        }

        boolean isIsoLanguage = Arrays.asList(Locale.getISOLanguages()).contains(langCode);
        if (!isIsoLanguage) {
            throw new InvalidLanguageCodeException("Nieznany kod języka ISO 639: " + langCode);
        }

        return new Locale(langCode);
    }

}
