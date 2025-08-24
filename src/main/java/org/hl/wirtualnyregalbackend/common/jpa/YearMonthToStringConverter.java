package org.hl.wirtualnyregalbackend.common.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.YearMonth;

@Converter(autoApply = true)
public class YearMonthToStringConverter implements AttributeConverter<YearMonth, String> {

    @Override
    public String convertToDatabaseColumn(java.time.YearMonth attribute) {
        return (attribute == null) ? null : attribute.toString();
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : YearMonth.parse(dbData);
    }

}
