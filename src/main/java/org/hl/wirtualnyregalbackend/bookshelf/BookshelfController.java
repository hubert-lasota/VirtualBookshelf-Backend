package org.hl.wirtualnyregalbackend.bookshelf;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/v1/bookshelves")
@AllArgsConstructor
class BookshelfController {

    private final BookshelfService bookshelfService;


    @PostMapping
    public ResponseEntity<?> createBookshelf(
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
    public ResponseEntity<?> findCurrentUserBookshelves(@AuthenticationPrincipal User user) {
        var bookshelves = bookshelfService.findUserBookshelves(user.getId());
        Map<String, Object> response = Map.of("bookshelves", bookshelves);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'BOOKSHELF', 'CREATE')")
    public ResponseEntity<?> updateBookshelf(
        @PathVariable
        Long id,
        @RequestBody
        @Validated(UpdateGroup.class)
        BookshelfMutationDto bookshelfDto
    ) {
        return ResponseEntity.ok(bookshelfService.updateBookshelf(id, bookshelfDto));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'BOOKSHELF', 'DELETE')")
    public ResponseEntity<?> deleteBookshelf(@PathVariable Long id) {
        bookshelfService.deleteBookshelf(id);
        return ResponseEntity.noContent().build();
    }

}
