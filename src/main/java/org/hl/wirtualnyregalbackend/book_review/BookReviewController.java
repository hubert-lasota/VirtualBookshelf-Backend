package org.hl.wirtualnyregalbackend.book_review;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book_review.dto.BookReviewCreateRequest;
import org.hl.wirtualnyregalbackend.common.review.ReviewPageResponse;
import org.hl.wirtualnyregalbackend.common.review.ReviewResponse;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/book-reviews")
@AllArgsConstructor
class BookReviewController {

    private final BookReviewService bookReviewService;


    @PostMapping
    public ReviewResponse createBookReview(
        @Validated(CreateGroup.class)
        @RequestBody
        BookReviewCreateRequest reviewDto,
        @AuthenticationPrincipal
        User user
    ) {
        return bookReviewService.createBookReview(reviewDto, user);
    }

    @PatchMapping("/{bookReviewId}")
    @PreAuthorize("hasPermission(#bookReviewId, 'BOOK_REVIEW', 'UPDATE')")
    public ReviewResponse updateBookReview(
        @PathVariable
        Long bookReviewId,
        @Validated(UpdateGroup.class)
        @RequestBody
        BookReviewCreateRequest reviewDto
    ) {
        return bookReviewService.updateBookReview(bookReviewId, reviewDto);
    }

    @DeleteMapping("/{bookReviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasPermission(#bookReviewId, 'BOOK_REVIEW', 'DELETE')")
    public void deleteBookReview(@PathVariable Long bookReviewId) {
        bookReviewService.deleteBookReview(bookReviewId);
    }

    @GetMapping
    public ReviewPageResponse findBookReviews(@RequestParam Long bookId, Pageable pageable) {
        return bookReviewService.findBookReviews(bookId, pageable);
    }

}
