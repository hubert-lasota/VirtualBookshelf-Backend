package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.book.model.dto.response.BookResponseDto;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public ResponseEntity<?> searchBooks(@RequestParam String query,
                                         @PageableDefault Pageable pageable) {
        Page<?> response = bookService.searchBooks(query, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBookById(@PathVariable Long id,
                                          @AuthenticationPrincipal User user) {
        BookResponseDto response = bookService.findBookById(id, user);
        return ResponseEntity.ok(response);
    }

}
