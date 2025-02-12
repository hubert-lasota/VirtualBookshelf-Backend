package org.hl.wirtualnyregalbackend.common.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Locale;

@Converter
public class LocaleToStringConverter implements AttributeConverter<Locale, String> {

    @Override
    public String convertToDatabaseColumn(Locale language) {
        return language == null ? null : language.toLanguageTag();
    }

    @Override
    public Locale convertToEntityAttribute(String language) {
        return language == null ? null : Locale.forLanguageTag(language);
    }

}
