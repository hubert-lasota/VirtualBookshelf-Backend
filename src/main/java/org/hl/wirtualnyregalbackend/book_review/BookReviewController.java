package org.hl.wirtualnyregalbackend.book_review;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book_review.dto.BookReviewCreateDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createBookReview(
        @Validated(CreateGroup.class)
        @RequestBody
        BookReviewCreateDto reviewDto,
        @AuthenticationPrincipal
        User user
    ) {
        return ResponseEntity.ok(bookReviewService.createBookReview(reviewDto, user));
    }

    @GetMapping
    public ResponseEntity<?> findBookReviews(@RequestParam Long bookId, Pageable pageable) {
        return ResponseEntity.ok(bookReviewService.findBookReviews(bookId, pageable));
    }

    @PatchMapping("/{bookReviewId}")
    public ResponseEntity<?> updateBookReview(
        @PathVariable
        Long bookReviewId,
        @Validated(UpdateGroup.class)
        @RequestBody
        BookReviewCreateDto reviewDto
    ) {
        return ResponseEntity.ok(bookReviewService.updateBookReview(bookReviewId, reviewDto));
    }

    @DeleteMapping("/{bookReviewId}")
    public ResponseEntity<?> deleteBookReview(@PathVariable Long bookReviewId) {
        bookReviewService.deleteBookReview(bookReviewId);
        return ResponseEntity.noContent().build();
    }

}
