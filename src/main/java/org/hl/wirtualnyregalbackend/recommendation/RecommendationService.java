package org.hl.wirtualnyregalbackend.recommendation;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.dto.BookPageResponse;
import org.hl.wirtualnyregalbackend.book.dto.BookResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
class RecommendationService {

    private final BookRecommendationRepository bookRecommendationRepository;

    public BookPageResponse findRecommendedBooks(User user, Pageable pageable) {
        Locale locale = LocaleContextHolder.getLocale();
        Page<BookResponse> page = bookRecommendationRepository
            .findRecommendedBooksForUser(user.getId(), pageable)
            .map((book) -> BookMapper.toBookResponse(book, locale));
        return BookPageResponse.from(page);
    }

}
