package org.hl.wirtualnyregalbackend.recommendation;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.BookService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class RecommendationService {

    private final BookRecommendationRepository bookRecommendationRepository;
    private final BookService bookService;

}
