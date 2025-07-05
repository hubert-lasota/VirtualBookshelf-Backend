package org.hl.wirtualnyregalbackend.bookshelf_book;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookWithBookshelfId;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.MoveBookshelfBookDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/v1/bookshelf-books")
@AllArgsConstructor
public class BookshelfBookController {

    private final BookshelfBookService bookshelfBookService;


    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(#body.bookshelfId, 'BOOKSHELF', 'CREATE')")
    public ResponseEntity<?> createBookshelfBook(
        @RequestPart("bookshelfBook")
        @Validated(CreateGroup.class)
        BookshelfBookWithBookshelfId body,
        @RequestPart(value = "cover", required = false)
        MultipartFile cover,
        UriComponentsBuilder uriBuilder
    ) {
        BookshelfBookResponseDto response = bookshelfBookService.createBookshelfBook(body.getBookshelfId(), body.getBookshelfBookDto(), cover);

        URI location = uriBuilder
            .path("/v1/bookshelf-books/{bookId}")
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{bookshelfBookId}")
    @PreAuthorize("hasPermission(#bookshelfBookId, 'BOOKSHELF_BOOK', 'READ')")
    public ResponseEntity<?> findBookshelfBook(@PathVariable Long bookshelfBookId) {
        var response = bookshelfBookService.findBookshelfBookById(bookshelfBookId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> findCurrentUserBookshelfBooks(@AuthenticationPrincipal User user) {
        var response = bookshelfBookService.findUserBookshelfBooks(user);
        Map<String, Object> responseMap = Map.of("books", response);
        return ResponseEntity.ok(responseMap);
    }

    @PatchMapping("/{bookshelfBookId}")
    @PreAuthorize("hasPermission(#bookshelfBookId, 'BOOKSHELF_BOOK', 'DELETE')")
    public ResponseEntity<?> updateBookshelfBook(
        @PathVariable
        Long bookshelfBookId,
        @RequestBody
        @Validated(UpdateGroup.class)
        BookshelfBookMutationDto bookshelfBookDto
    ) {
        var response = bookshelfBookService.updateBookshelfBook(bookshelfBookId, bookshelfBookDto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{bookshelfBookId}/move")
    @PreAuthorize("hasPermission(#request.bookshelfId, 'BOOKSHELF', 'UPDATE')")
    public ResponseEntity<?> moveBookshelfBook(@PathVariable Long bookshelfBookId,
                                               @Valid @RequestBody MoveBookshelfBookDto request) {
        var response = bookshelfBookService.moveBookshelfBook(bookshelfBookId, request.bookshelfId());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{bookshelfBookId}/read")
    @PreAuthorize("hasPermission(#bookshelfBookId, 'BOOKSHELF_BOOK', 'UPDATE')")
    public ResponseEntity<?> markBookshelfBookAsRead(@PathVariable Long bookshelfBookId) {
        var response = bookshelfBookService.markBookshelfBookAsRead(bookshelfBookId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{bookshelfBookId}/reading")
    @PreAuthorize("hasPermission(#bookshelfBookId, 'BOOKSHELF_BOOK', 'UPDATE')")
    public ResponseEntity<?> markBookshelfBookAsReading(@PathVariable Long bookshelfBookId) {
        var response = bookshelfBookService.markBookshelfBookAsReading(bookshelfBookId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookshelfBookId}")
    @PreAuthorize("hasPermission(#bookshelfBookId, 'BOOKSHELF_BOOK', 'DELETE')")
    public ResponseEntity<?> deleteBookshelfBook(@PathVariable Long bookshelfBookId) {
        bookshelfBookService.deleteBookshelfBook(bookshelfBookId);
        return ResponseEntity.noContent().build();
    }

}
