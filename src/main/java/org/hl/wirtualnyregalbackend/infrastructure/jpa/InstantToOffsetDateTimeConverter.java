package org.hl.wirtualnyregalbackend.infrastructure.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Converter(autoApply = true)
public class InstantToOffsetDateTimeConverter implements AttributeConverter<Instant, OffsetDateTime> {

    @Override
    public OffsetDateTime convertToDatabaseColumn(Instant attribute) {
        return attribute == null ? null : attribute.atOffset(ZoneOffset.UTC);
    }

    @Override
    public Instant convertToEntityAttribute(OffsetDateTime dbData) {
        return dbData == null ? null : dbData.toInstant();
    }

}
