package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.book.dto.BookMutationDto;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.common.model.PageResponseDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/books")
class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping
    public ResponseEntity<?> createBook(@Validated(CreateGroup.class) @RequestPart("book") BookMutationDto bookMutationDto,
                                        @RequestPart("cover") MultipartFile coverFile) {
        BookResponseDto response = bookService.createBook(bookMutationDto, coverFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> findBooks(@RequestParam String query,
                                       @PageableDefault Pageable pageable) {
        PageResponseDto<?> response = bookService.findBooks(query, pageable);
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
                                        @Validated(UpdateGroup.class) @RequestPart("book") BookMutationDto bookMutationDto,
                                        @RequestPart("cover") MultipartFile coverFile) {
        BookResponseDto response = bookService.updateBook(id, bookMutationDto, coverFile);
        return ResponseEntity.ok(response);
    }

}
