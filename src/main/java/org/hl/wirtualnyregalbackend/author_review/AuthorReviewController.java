package org.hl.wirtualnyregalbackend.author_review;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author_review.dto.AuthorReviewCreateRequest;
import org.hl.wirtualnyregalbackend.common.review.ReviewPageResponse;
import org.hl.wirtualnyregalbackend.common.review.ReviewRequest;
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
@RequestMapping("/v1/author-reviews")
@AllArgsConstructor
class AuthorReviewController {

    private final AuthorReviewQueryService query;
    private final AuthorReviewCommandService command;

    @PostMapping
    public ReviewResponse createAuthorReview(
        @Validated(CreateGroup.class)
        @RequestBody
        AuthorReviewCreateRequest reviewCreateRequest,
        @AuthenticationPrincipal
        User user
    ) {
        return command.createAuthorReview(reviewCreateRequest, user);
    }

    @PatchMapping("/{authorReviewId}")
    @PreAuthorize("hasPermission(#authorReviewId, 'AUTHOR_REVIEW', 'UPDATE')")
    public ReviewResponse updateAuthorReview(
        @PathVariable
        Long authorReviewId,
        @Validated(UpdateGroup.class)
        @RequestBody
        ReviewRequest reviewRequest
    ) {
        return command.updateAuthorReview(authorReviewId, reviewRequest);
    }

    @DeleteMapping("/{authorReviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasPermission(#authorReviewId, 'AUTHOR_REVIEW', 'DELETE')")
    public void deleteAuthorReview(@PathVariable Long authorReviewId) {
        command.deleteAuthorReview(authorReviewId);
    }

    @GetMapping
    public ReviewPageResponse findAuthorReviews(@RequestParam Long authorId, Pageable pageable) {
        return query.findAuthorReviews(authorId, pageable);
    }

}
