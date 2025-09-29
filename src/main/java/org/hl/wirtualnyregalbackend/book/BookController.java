package org.hl.wirtualnyregalbackend.book;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.dto.BookDetailsResponse;
import org.hl.wirtualnyregalbackend.book.dto.BookPageResponse;
import org.hl.wirtualnyregalbackend.book.dto.BookRequest;
import org.hl.wirtualnyregalbackend.book.dto.BookResponse;
import org.hl.wirtualnyregalbackend.book.model.BookFilter;
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

    private final BookQueryService query;
    private final BookCommandService command;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(
        @Validated(CreateGroup.class)
        @RequestPart("book")
        BookRequest bookRequest,
        @RequestPart("cover")
        MultipartFile coverFile,
        UriComponentsBuilder uriBuilder
    ) {
        BookResponse response = command.createBook(bookRequest, coverFile);

        URI location = uriBuilder
            .path("/v1/books/{bookId}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PatchMapping("/{id}")
    public BookResponse updateBook(
        @PathVariable
        Long id,
        @Validated(UpdateGroup.class)
        @RequestPart("book")
        BookRequest bookRequest,
        @RequestPart("cover")
        MultipartFile coverFile
    ) {
        return command.updateBook(id, bookRequest, coverFile);
    }

    @GetMapping
    public BookPageResponse findBooks(@Valid BookFilter bookFilter,
                                      @PageableDefault Pageable pageable) {
        return query.findBooks(bookFilter, pageable);
    }

    @GetMapping("/{id}")
    public BookDetailsResponse findBookDetailsById(@PathVariable Long id,
                                                   @AuthenticationPrincipal User user) {
        return query.findBookDetailsById(id, user);
    }

}
