package org.hl.wirtualnyregalbackend.book_format;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatListResponse;
import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.hl.wirtualnyregalbackend.book_format.exception.BookFormatNotFoundException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class BookFormatQueryService {

    private final BookFormatRepository repository;


    public BookFormatListResponse findBookFormats(Boolean availableInBookshelf, User user) {
        Specification<BookFormat> spec = availableInBookshelf != null
            ? BookFormatSpecification.availableInBookshelf(availableInBookshelf, user)
            : Specification.where(null);

        Locale locale = LocaleContextHolder.getLocale();
        List<BookFormatDto> formats = repository
            .findAll(spec)
            .stream()
            .map(format -> BookFormatMapper.toBookFormatDto(format, locale))
            .toList();
        return new BookFormatListResponse(formats);
    }

    public BookFormat findBookFormatById(Long id) throws BookFormatNotFoundException {
        Optional<BookFormat> formatOpt = id != null ? repository.findById(id) : Optional.empty();
        return formatOpt.orElseThrow(() -> {
            log.warn("BookFormat not found with ID: {}", id);
            return new BookFormatNotFoundException(id);
        });
    }

}
