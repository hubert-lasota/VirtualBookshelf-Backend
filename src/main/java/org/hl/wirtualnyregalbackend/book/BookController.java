package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.book.model.dto.BookMutationDto;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookResponseDto;
import org.hl.wirtualnyregalbackend.common.PageResponseDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    public ResponseEntity<?> createBook(@Validated(CreateGroup.class) @RequestBody BookMutationDto bookMutationDto) {
        BookResponseDto response = bookService.createBook(bookMutationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> searchBooks(@RequestParam String query,
                                         @PageableDefault Pageable pageable) {
        PageResponseDto<?> response = bookService.searchBooks(query, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBookById(@PathVariable Long id,
                                          @AuthenticationPrincipal User user) {
        BookResponseDto response = bookService.findBookById(id, user);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id,
                                        @Validated(UpdateGroup.class) @RequestBody BookMutationDto bookMutationDto) {
        BookResponseDto response = bookService.updateBook(id, bookMutationDto);
        return ResponseEntity.ok(response);
    }

}
