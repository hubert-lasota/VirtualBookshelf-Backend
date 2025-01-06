package org.hl.wirtualnyregalbackend.infrastructure.book;

import org.hl.wirtualnyregalbackend.application.book.BookService;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookRatingRequest;
import org.hl.wirtualnyregalbackend.infrastructure.security.ActionType;
import org.hl.wirtualnyregalbackend.infrastructure.security.ResourceType;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;
import org.hl.wirtualnyregalbackend.infrastructure.security.annotation.PermissionAccessResource;
import org.hl.wirtualnyregalbackend.infrastructure.security.annotation.RequiresPermission;
import org.hl.wirtualnyregalbackend.infrastructure.security.annotation.ResourceId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@PermissionAccessResource(ResourceType.BOOK)
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/rating")
    public ResponseEntity<?> addBookRating(@RequestParam String isbn,
                                           @RequestBody BookRatingRequest request,
                                           @AuthenticationPrincipal User user) {
        Long response = bookService.addBookRating(isbn, user, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/rating/{bookRatingId}")
    @RequiresPermission(value = ActionType.DELETE, resourceType = ResourceType.BOOK_RATING)
    public ResponseEntity<?> deleteBookRating(@PathVariable @ResourceId Long bookRatingId) {
        bookService.deleteBookRating(bookRatingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> searchBooks(@RequestParam("q") String query,
                                         @RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<?> response = bookService.searchBooks(query, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBookById(@PathVariable String id,
                                            @RequestParam(defaultValue = "true") boolean details) {
        var response = details ? bookService.findBookResponseById(id) : bookService.findBookDetailsById(id);
        return ResponseEntity.ok(response);
    }


}
