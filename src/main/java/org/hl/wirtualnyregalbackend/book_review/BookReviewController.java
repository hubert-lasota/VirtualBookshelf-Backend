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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/book-reviews")
@AllArgsConstructor
// TODO add pre authorize to update / delete
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

    @GetMapping
    public ReviewPageResponse findBookReviews(@RequestParam Long bookId, Pageable pageable) {
        return bookReviewService.findBookReviews(bookId, pageable);
    }

    @PatchMapping("/{bookReviewId}")
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
    public void deleteBookReview(@PathVariable Long bookReviewId) {
        bookReviewService.deleteBookReview(bookReviewId);
    }

}
