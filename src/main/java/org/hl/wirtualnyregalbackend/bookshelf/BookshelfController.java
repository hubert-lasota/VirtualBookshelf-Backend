package org.hl.wirtualnyregalbackend.bookshelf;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfListResponse;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfRequest;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponse;
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

    private final BookshelfCommandService command;
    private final BookshelfQueryService query;


    @PostMapping
    public ResponseEntity<BookshelfResponse> createBookshelf(
        @Validated(CreateGroup.class)
        @RequestBody
        BookshelfRequest bookshelfRequest,
        @AuthenticationPrincipal User user,
        UriComponentsBuilder uriBuilder
    ) {
        BookshelfResponse response = command.createBookshelf(bookshelfRequest, user);

        URI location = uriBuilder
            .path("/v1/bookshelves/{bookshelfId}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'BOOKSHELF', 'CREATE')")
    public BookshelfResponse updateBookshelf(
        @PathVariable
        Long id,
        @RequestBody
        @Validated(UpdateGroup.class)
        BookshelfRequest bookshelfRequest
    ) {
        return command.updateBookshelf(id, bookshelfRequest);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'BOOKSHELF', 'DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookshelf(@PathVariable Long id) {
        command.deleteBookshelf(id);
    }

    @GetMapping
    public BookshelfListResponse findCurrentUserBookshelves(@AuthenticationPrincipal User user) {
        return query.findUserBookshelves(user);
    }

}
