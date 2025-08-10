package org.hl.wirtualnyregalbackend.book_format;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatListResponseDto;
import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookFormatService {

    private final BookFormatRepository bookFormatRepository;


    public BookFormatListResponseDto findBookFormats(Boolean availableInBookshelf, User user) {
        Specification<BookFormat> spec = availableInBookshelf != null
            ? BookFormatSpecification.availableInBookshelf(availableInBookshelf, user)
            : Specification.where(null);

        Locale locale = LocaleContextHolder.getLocale();
        List<BookFormatDto> formats = bookFormatRepository
            .findAll(spec)
            .stream()
            .map(format -> BookFormatMapper.toBookFormatDto(format, locale))
            .toList();
        return new BookFormatListResponseDto(formats);
    }

    public BookFormat findBookFormatById(Long id) throws EntityNotFoundException {
        Optional<BookFormat> formatOpt = id != null ? bookFormatRepository.findById(id) : Optional.empty();
        return formatOpt.orElseThrow(() -> new EntityNotFoundException("Book format with id = '%d' not found.".formatted(id)));
    }
}
