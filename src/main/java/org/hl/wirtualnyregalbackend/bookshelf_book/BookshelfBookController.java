package org.hl.wirtualnyregalbackend.bookshelf_book;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookWithBookshelfId;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/bookshelf-books")
@AllArgsConstructor
public class BookshelfBookController {

    private final BookshelfBookService bookshelfBookService;


    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(#bookshelfBookDto.bookshelfId, 'BOOKSHELF', 'CREATE')")
    public ResponseEntity<?> createBookshelfBook(
        @RequestPart("bookshelfBook")
        @Validated(CreateGroup.class)
        BookshelfBookWithBookshelfId bookshelfBookDto,
        @RequestPart(value = "cover", required = false)
        MultipartFile cover,
        UriComponentsBuilder uriBuilder
    ) {
        BookshelfBookResponseDto response = bookshelfBookService.createBookshelfBook(bookshelfBookDto, cover);

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

    @PatchMapping("/{bookshelfBookId}")
    @PreAuthorize("hasPermission(#bookshelfBookId, 'BOOKSHELF_BOOK', 'DELETE')")
    public ResponseEntity<?> updateBookshelfBook(@PathVariable Long bookshelfBookId, @RequestBody @Validated(UpdateGroup.class) BookshelfBookWithBookshelfId bookshelfBookDto) {
        var response = bookshelfBookService.updateBookshelfBook(bookshelfBookId, bookshelfBookDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookshelfBookId}")
    @PreAuthorize("hasPermission(#bookshelfBookId, 'BOOKSHELF_BOOK', 'DELETE')")
    public ResponseEntity<?> deleteBookshelfBook(
        @PathVariable Long bookshelfBookId) {
        bookshelfBookService.deleteBookshelfBook(bookshelfBookId);
        return ResponseEntity.noContent().build();
    }

}
