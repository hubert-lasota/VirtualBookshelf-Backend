package org.hl.wirtualnyregalbackend.infrastructure.book;

import org.hl.wirtualnyregalbackend.application.book.BookRating;
import org.hl.wirtualnyregalbackend.application.book.BookService;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.request.BookRatingRequest;
import org.hl.wirtualnyregalbackend.infrastructure.common.ResourceType;
import org.hl.wirtualnyregalbackend.infrastructure.recommendation.Recommendation;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;
import org.hl.wirtualnyregalbackend.infrastructure.security.annotation.RequiresPermission;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/{bookId}/rating")
    @Recommendation(resourceIdParamName = "bookId")
    public ResponseEntity<?> addBookRating(@PathVariable String bookId,
                                           @RequestBody BookRatingRequest request,
                                           @AuthenticationPrincipal User user) {
        BookRating bookRating = bookService.addBookRating(bookId, user, request);
        return ResponseEntity.ok(bookRating.getId());
    }

    @DeleteMapping("/rating/{bookRatingId}")
    @RequiresPermission(resourceIdParamName = "bookRatingId", resourceType = ResourceType.BOOK_RATING)
    public ResponseEntity<?> deleteBookRating(@PathVariable Long bookRatingId) {
        bookService.deleteBookRating(bookRatingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> searchBooks(@RequestParam("q") String query,
                                         @PageableDefault Pageable pageable) {
        Page<?> response = bookService.searchBooks(query, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Recommendation(resourceIdParamName = "id")
    public ResponseEntity<?> findBookById(@PathVariable String id,
                                          @RequestParam(defaultValue = "true") boolean details,
                                          @PageableDefault(size = 20) @Qualifier("rating") Pageable pageable,
                                          @AuthenticationPrincipal User user) {
        var response = details ? bookService.findBookResponseById(id) : bookService.findBookDetailsById(id, user, pageable);
        return ResponseEntity.ok(response);
    }

}
