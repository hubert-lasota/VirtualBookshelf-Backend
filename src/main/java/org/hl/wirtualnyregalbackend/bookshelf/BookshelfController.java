package org.hl.wirtualnyregalbackend.bookshelf;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookCoverOrderDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfCreateDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfUpdateDto;
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
@AllArgsConstructor
class BookshelfController {

    private final BookshelfService bookshelfService;


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


}
