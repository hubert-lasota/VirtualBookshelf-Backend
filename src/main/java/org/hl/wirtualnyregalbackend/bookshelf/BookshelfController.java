package org.hl.wirtualnyregalbackend.bookshelf;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfListResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/bookshelves")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BookshelfController {

    private final BookshelfService bookshelfService;


    @PostMapping
    public ResponseEntity<BookshelfResponseDto> createBookshelf(
        @Validated(CreateGroup.class)
        @RequestBody
        BookshelfMutationDto bookshelfDto,
        @AuthenticationPrincipal User user,
        UriComponentsBuilder uriBuilder
    ) {
        BookshelfResponseDto response = bookshelfService.createBookshelf(bookshelfDto, user);

        URI location = uriBuilder
            .path("/v1/bookshelves/{bookshelfId}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public BookshelfListResponseDto findCurrentUserBookshelves(@AuthenticationPrincipal User user) {
        return bookshelfService.findUserBookshelves(user.getId());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'BOOKSHELF', 'CREATE')")
    public BookshelfResponseDto updateBookshelf(
        @PathVariable
        Long id,
        @RequestBody
        @Validated(UpdateGroup.class)
        BookshelfMutationDto bookshelfDto
    ) {
        return bookshelfService.updateBookshelf(id, bookshelfDto);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'BOOKSHELF', 'DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookshelf(@PathVariable Long id) {
        bookshelfService.deleteBookshelf(id);
    }

}
