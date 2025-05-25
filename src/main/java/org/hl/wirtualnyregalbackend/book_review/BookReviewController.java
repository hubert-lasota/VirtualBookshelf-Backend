package org.hl.wirtualnyregalbackend.book_review;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book-reviews")
public class BookReviewController {

    private final BookReviewService bookReviewService;

    public BookReviewController(BookReviewService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

//
//    @PostMapping
//    public ResponseEntity<?> addBookReview(@RequestParam Long bookId,
//                                           @RequestBody @Validated(CreateGroup.class) ReviewDto review,
//                                           @AuthenticationPrincipal User user) {
//        BookReview bookReview = bookReviewService.addBookReview(bookId, user, review);
//        return ResponseEntity.ok(bookReview.getId());
//    }
//
//    @DeleteMapping("/{bookRatingId}")
//    @PreAuthorize("hasRole('ADMIN') or @bookReviewPermissionEvaluator.isAuthor(#bookRatingId, #principal)")
//    public ResponseEntity<?> deleteBookReview(@PathVariable Long bookRatingId) {
//        bookService.deleteBookRating(bookRatingId);
//        return ResponseEntity.ok().build();
//    }

}
