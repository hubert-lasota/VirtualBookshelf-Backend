package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.bookshelf.dto.*;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/bookshelves")
class BookshelfController {

    private final BookshelfService bookshelfService;

    public BookshelfController(BookshelfService bookshelfService) {
        this.bookshelfService = bookshelfService;
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBookshelf(
        @Validated(CreateGroup.class) @RequestPart("bookshelf")
        BookshelfCreateDto bookshelfCreateDto,
        @RequestPart(value = "covers", required = false) List<MultipartFile> covers,
        @RequestPart(value = "metadata", required = false) List<BookCoverOrderDto> bookCoverOrderDtos,
        @AuthenticationPrincipal User user,
        UriComponentsBuilder uriBuilder
    ) {
        BookshelfResponseDto response = bookshelfService.createBookshelf(
            bookshelfCreateDto,
            covers != null ? covers : Collections.emptyList(),
            bookCoverOrderDtos != null ? bookCoverOrderDtos : Collections.emptyList(),
            user);

        URI location = uriBuilder
            .path("/v1/bookshelves/{bookshelfId}")
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<?> findCurrentUserBookshelves(@AuthenticationPrincipal User user) {
        var bookshelves = bookshelfService.findUserBookshelves(user.getId());
        Map<String, Object> response = Map.of("bookshelves", bookshelves);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(#id, 'BOOKSHELF', 'CREATE')")
    public ResponseEntity<?> updateBookshelf(
        @PathVariable
        Long id,
        @RequestPart
        @Validated(UpdateGroup.class)
        BookshelfUpdateDto bookshelfUpdateDto,
        @RequestPart(value = "covers", required = false)
        List<MultipartFile> covers,
        @RequestPart(value = "metadata", required = false)
        List<BookCoverOrderDto> bookCoverOrderDtos
    ) {
        return ResponseEntity.ok(bookshelfService.updateBookshelf(id, bookshelfUpdateDto, covers, bookCoverOrderDtos));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'BOOKSHELF', 'DELETE')")
    public ResponseEntity<?> deleteBookshelf(@PathVariable Long id) {
        bookshelfService.deleteBookshelf(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{bookshelfId}/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(#bookshelfId, 'BOOKSHELF', 'CREATE')")
    public ResponseEntity<?> createBookshelfBook(
        @PathVariable
        Long bookshelfId,
        @RequestPart("bookshelfBook")
        @Validated(CreateGroup.class)
        BookshelfBookMutationDto bookshelfBookDto,
        @RequestPart(value = "cover", required = false)
        MultipartFile cover,
        UriComponentsBuilder uriBuilder
    ) {
        BookshelfBookResponseDto response = bookshelfService.createBookshelfBook(bookshelfId, bookshelfBookDto, cover);

        URI location = uriBuilder
            .path("/v1/bookshelves/{bookshelfId}/books/{bookId}")
            .buildAndExpand(bookshelfId, response.getId())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{bookshelfId}/books/{bookId}")
    @PreAuthorize("hasPermission(#bookshelfId, 'BOOKSHELF', 'READ')")
    public ResponseEntity<?> findBookshelfBook(@PathVariable Long bookshelfId, @PathVariable Long bookId) {
        var response = bookshelfService.findBookshelfBook(bookshelfId, bookId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{bookshelfId}/books/{bookshelfBookId}")
    @PreAuthorize("hasPermission(#bookshelfId, 'BOOKSHELF', 'DELETE')")
    public ResponseEntity<?> updateBookshelfBook(@PathVariable Long bookshelfId,
                                                 @PathVariable Long bookshelfBookId,
                                                 @RequestBody @Validated(UpdateGroup.class) BookshelfBookMutationDto bookshelfBookDto) {
        BookshelfBookResponseDto response = bookshelfService.updateBookshelfBook(bookshelfId, bookshelfBookId, bookshelfBookDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookshelfId}/books/{bookId}")
    @PreAuthorize("hasPermission(#bookshelfId, 'BOOKSHELF', 'DELETE')")
    public ResponseEntity<?> deleteBookshelfBook(@PathVariable Long bookshelfId,
                                                 @PathVariable Long bookId) {
        bookshelfService.deleteBookshelfBook(bookshelfId, bookId);
        return ResponseEntity.noContent().build();
    }

}
