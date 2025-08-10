package org.hl.wirtualnyregalbackend.book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.dto.BookMutationDto;
import org.hl.wirtualnyregalbackend.book.dto.BookPageResponseDto;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
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
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDto> createBook(@Validated(CreateGroup.class) @RequestPart("book") BookMutationDto bookMutationDto,
                                                      @RequestPart("cover") MultipartFile coverFile,
                                                      UriComponentsBuilder uriBuilder) {
        BookResponseDto response = bookService.createBook(bookMutationDto, coverFile);

        URI location = uriBuilder
            .path("/v1/books/{bookId}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public BookPageResponseDto findBooks(@RequestParam String query,
                                         @PageableDefault Pageable pageable) {
        return bookService.findBooks(query, pageable);
    }

    @GetMapping("/{id}")
    public BookResponseDto findBookById(@PathVariable Long id,
                                        @AuthenticationPrincipal User user) {
        return bookService.findBookById(id, user);
    }

    @PatchMapping("/{id}")
    public BookResponseDto updateBook(@PathVariable Long id,
                                      @Validated(UpdateGroup.class) @RequestPart("book") BookMutationDto bookMutationDto,
                                      @RequestPart("cover") MultipartFile coverFile) {
        return bookService.updateBook(id, bookMutationDto, coverFile);
    }

}
