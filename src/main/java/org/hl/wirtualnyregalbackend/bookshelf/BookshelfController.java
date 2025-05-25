package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/v1/bookshelves")
class BookshelfController {

    private final BookshelfService bookshelfService;

    public BookshelfController(BookshelfService bookshelfService) {
        this.bookshelfService = bookshelfService;
    }


    @PostMapping
    public ResponseEntity<?> createBookshelfForCurrentUser(@Validated(CreateGroup.class) @RequestBody BookshelfMutationDto bookshelfMutationDto,
                                                           @AuthenticationPrincipal User user) {
        BookshelfResponseDto response = bookshelfService.createBookshelf(bookshelfMutationDto, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> findCurrentUserBookshelves(@AuthenticationPrincipal User user) {
        Collection<BookshelfResponseDto> bookshelves = bookshelfService.findUserBookshelves(user.getId());
        Map<String, Object> response = Map.of("bookshelves", bookshelves);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{bookshelfId}/book-editions")
    @PreAuthorize("hasRole('ADMIN') or @bookshelfPermissionEvaluator()")
    public ResponseEntity<?> addBookToBookshelf(@RequestParam Long bookshelfId,
                                                @RequestBody Long bookEditionId) {
        bookshelfService.addBookToBookshelf(bookshelfId, bookEditionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookshelfId}/book-editions/{bookEditionId}")
    @PreAuthorize("hasPermission(#bookshelfId, 'BOOKSHELF', 'DELETE')")
    public ResponseEntity<?> removeBookFromBookshelf(@RequestParam Long bookshelfId,
                                                     @RequestParam Long bookEditionId) {
        bookshelfService.removeBookFromBookshelf(bookshelfId, bookEditionId);
        return ResponseEntity.ok().build();
    }

}
