package org.hl.wirtualnyregalbackend.common.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Locale;

@Converter(autoApply = true)
public class LocaleToStringConverter implements AttributeConverter<Locale, String> {

    @Override
    public String convertToDatabaseColumn(Locale language) {
        return language == null ? null : language.getLanguage();
    }

    @Override
    public Locale convertToEntityAttribute(String language) {
        return language == null ? null : new Locale(language);
    }

}
