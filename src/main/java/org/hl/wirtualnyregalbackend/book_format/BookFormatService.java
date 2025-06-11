package org.hl.wirtualnyregalbackend.book_format;

import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class BookFormatService {

    private final BookFormatRepository bookFormatRepository;

    BookFormatService(BookFormatRepository bookFormatRepository) {
        this.bookFormatRepository = bookFormatRepository;
    }

    public List<BookFormatDto> findBookFormats() {
        Locale locale = LocaleContextHolder.getLocale();
        return bookFormatRepository.findAll()
            .stream()
            .map(format -> BookFormatMapper.toBookFormatDto(format, locale))
            .toList();
    }

    public BookFormat findBookFormatById(Long id) throws EntityNotFoundException {
        return bookFormatRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book format with id = '%d' not found.".formatted(id)));
    }
}
