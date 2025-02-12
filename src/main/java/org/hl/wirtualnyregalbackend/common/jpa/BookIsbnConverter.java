package org.hl.wirtualnyregalbackend.common.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.hl.wirtualnyregalbackend.book.model.BookIsbn;

@Converter
public class BookIsbnConverter implements AttributeConverter<BookIsbn, String> {

    @Override
    public String convertToDatabaseColumn(BookIsbn bookIsbn) {
        return bookIsbn != null ? bookIsbn.getStandardizedIsbn() : null;
    }

    @Override
    public BookIsbn convertToEntityAttribute(String isbn) {
        return isbn != null ? new BookIsbn(isbn) : null;
    }

}
