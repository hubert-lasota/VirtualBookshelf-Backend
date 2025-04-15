package org.hl.wirtualnyregalbackend.bookshelf;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/bookshelf-books")
public class BookshelfBookController {

    private final BookshelfService bookshelfService;

    public BookshelfBookController(BookshelfService bookshelfService) {
        this.bookshelfService = bookshelfService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or @bookshelfPermissionEvaluator()")
    public ResponseEntity<?> addBookToBookshelf(@RequestParam Long bookshelfId,
                                                @RequestParam Long bookId) {
        bookshelfService.addBookToBookshelf(bookshelfId, bookId);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping
    @PreAuthorize("has")
    public ResponseEntity<?> removeBookFromBookshelf(@RequestParam Long bookshelfId,
                                                     @RequestParam Long bookId) {
        bookshelfService.removeBookFromBookshelf(bookshelfId, bookId);
        return ResponseEntity.ok().build();
    }
}
