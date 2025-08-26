package org.hl.wirtualnyregalbackend.recommendation;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.dto.BookPageResponse;
import org.hl.wirtualnyregalbackend.book.dto.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class RecommendationService {

    private final BookRecommendationRepository bookRecommendationRepository;

    public BookPageResponse findRecommendedBooks(User user, Pageable pageable) {
        Page<BookResponse> page = bookRecommendationRepository
            .findRecommendedBooksForUser(user.getId(), pageable)
            .map(BookMapper::toBookResponse);
        return BookPageResponse.from(page);
    }

}
