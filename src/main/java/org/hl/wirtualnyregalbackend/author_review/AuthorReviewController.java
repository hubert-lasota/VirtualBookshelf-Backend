package org.hl.wirtualnyregalbackend.author_review;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author_review.dto.AuthorReviewCreateDto;
import org.hl.wirtualnyregalbackend.book_review.dto.BookReviewCreateDto;
import org.hl.wirtualnyregalbackend.common.review.ReviewPageResponseDto;
import org.hl.wirtualnyregalbackend.common.review.ReviewResponseDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/author-reviews")
@AllArgsConstructor
class AuthorReviewController {

    private final AuthorReviewService authorReviewService;

    @PostMapping
    public ReviewResponseDto createAuthorReview(
        @Validated(CreateGroup.class)
        @RequestBody
        AuthorReviewCreateDto reviewDto,
        @AuthenticationPrincipal
        User user
    ) {
        return authorReviewService.createAuthorReview(reviewDto, user);
    }

    @GetMapping
    public ReviewPageResponseDto findBookReviews(@RequestParam Long authorId, Pageable pageable) {
        return authorReviewService.findAuthorReviews(authorId, pageable);
    }

    @PatchMapping("/{authorReviewId}")
    public ReviewResponseDto updateBookReview(
        @PathVariable
        Long authorReviewId,
        @Validated(UpdateGroup.class)
        @RequestBody
        BookReviewCreateDto reviewDto
    ) {
        return authorReviewService.updateBookReview(authorReviewId, reviewDto);
    }

    @DeleteMapping("/{authorReviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookReview(@PathVariable Long authorReviewId) {
        authorReviewService.deleteBookReview(authorReviewId);
    }

}
