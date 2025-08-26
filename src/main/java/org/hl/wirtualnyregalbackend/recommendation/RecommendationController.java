package org.hl.wirtualnyregalbackend.recommendation;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.dto.BookPageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/recommendations")
@AllArgsConstructor
class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/books")
    public BookPageResponse findBooks(@AuthenticationPrincipal User user, Pageable pageable) {
        return recommendationService.findRecommendedBooks(user, pageable);
    }

}
