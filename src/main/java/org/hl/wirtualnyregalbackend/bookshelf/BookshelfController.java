package org.hl.wirtualnyregalbackend.bookshelf;

import jakarta.validation.Valid;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfRequest;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfResponse;
import org.hl.wirtualnyregalbackend.recommendation.Recommendation;
import org.hl.wirtualnyregalbackend.security.annotation.RequiresPermission;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bookshelf")
public class BookshelfController {

    private final BookshelfService bookshelfService;

    public BookshelfController(BookshelfService bookshelfService) {
        this.bookshelfService = bookshelfService;
    }

    @PostMapping
    public ResponseEntity<?> createBookshelfForCurrentUser(@Valid @RequestBody BookshelfRequest bookshelfRequest,
                                                           @AuthenticationPrincipal User user) {
        Long bookshelfId = bookshelfService.createBookshelf(bookshelfRequest, user);
        return ResponseEntity.ok(bookshelfId);
    }

    @PostMapping("/addBook")
    @RequiresPermission(resourceIdParamName = "bookshelfId")
    @Recommendation(resourceIdParamName = "bookId")
    public ResponseEntity<?> addBookToBookshelf(@RequestParam Long bookshelfId,
                                                @RequestParam String bookId) {
        bookshelfService.addBookToBookshelf(bookshelfId, bookId);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/removeBook")
    @RequiresPermission(resourceIdParamName = "bookshelfId")
    public ResponseEntity<?> removeBookFromBookshelf(@RequestParam Long bookshelfId,
                                                     @RequestParam Long bookId) {
        bookshelfService.removeBookFromBookshelf(bookshelfId, bookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> findCurrentUserBookshelves(@AuthenticationPrincipal User user) {
        Collection<BookshelfResponse> bookshelves = bookshelfService.findUserBookshelves(user.getId());
        Map<String, Object> response = new HashMap<>();
        response.put("bookshelves", bookshelves);
        return ResponseEntity.ok(response);
    }

}
