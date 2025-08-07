package org.hl.wirtualnyregalbackend.book;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.dto.BookMutationDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/books")
@AllArgsConstructor
class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> createBook(@Validated(CreateGroup.class) @RequestPart("book") BookMutationDto bookMutationDto,
                                        @RequestPart("cover") MultipartFile coverFile,
                                        UriComponentsBuilder uriBuilder) {
        var response = bookService.createBook(bookMutationDto, coverFile);

        URI location = uriBuilder
            .path("/v1/books/{bookId}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<?> findBooks(@RequestParam String query,
                                       @PageableDefault Pageable pageable) {
        var response = bookService.findBooks(query, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBookById(@PathVariable Long id,
                                          @AuthenticationPrincipal User user) {
        var response = bookService.findBookById(id, user);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id,
                                        @Validated(UpdateGroup.class) @RequestPart("book") BookMutationDto bookMutationDto,
                                        @RequestPart("cover") MultipartFile coverFile) {
        var response = bookService.updateBook(id, bookMutationDto, coverFile);
        return ResponseEntity.ok(response);
    }

}
